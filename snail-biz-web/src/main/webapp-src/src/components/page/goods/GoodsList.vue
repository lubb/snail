<template>
    <div class="content-wrapper">
        <el-row class="content-row">
            <el-col class="height-init" :span="3">
                <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
            </el-col>
            <el-col class="height-init" :span="21">
                <div class="container">
                    <el-row class="handle-box">
                        <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                        <el-button
                                type="primary"
                                icon="el-icon-plus"
                                class="handle-del"
                                @click="addUser"
                        >新增</el-button>
                        <el-button
                                type="primary"
                                icon="el-icon-delete"
                                class="handle-del"
                                @click="delAllSelection"
                        >批量删除</el-button>
                    </el-row>
                    <el-row class="list-table-row">
                        <el-table
                                :data="tableData"
                                border
                                class="table"
                                ref="multipleTable"
                                height="100%"
                                header-cell-class-name="table-header"
                                @selection-change="handleSelectionChange"
                        >
                            <el-table-column type="selection" width="55" align="center"></el-table-column>
                            <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                            <el-table-column prop="title" label="标题" show-overflow-tooltip />
                            <el-table-column prop="categoryName" label="商品分类"></el-table-column>
                            <el-table-column prop="brand" label="商品品牌"></el-table-column>
                            <el-table-column prop="money" label="金额">
                                <template slot-scope="scope">
                                    ¥{{scope.row.money}}
                                </template>
                            </el-table-column>
                            <el-table-column label="商品图片" align="center">
                                <template slot-scope="scope">
                                    <el-image
                                            class="table-td-thumb"
                                            :src="scope.row.img"
                                            :preview-src-list="[scope.row.img]"
                                    ></el-image>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip>
                                <template slot-scope="scope" >
                                    {{renderTime(scope.row.createTime)}}
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="140" align="center">
                                <template slot-scope="scope">
                                    <el-button
                                            type="text"
                                            icon="el-icon-edit"
                                            @click="handleEdit(scope.$index, scope.row)"
                                    >编辑</el-button>
                                    <el-button
                                            type="text"
                                            icon="el-icon-delete"
                                            class="red"
                                            @click="handleDelete(scope.$index, scope.row)"
                                    >删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-row>
                    <el-row class="pagination-self">
                        <el-pagination
                                background
                                layout="total, sizes, prev, pager, next"
                                :current-page="query.pageNo"
                                :page-size="query.pageSize"
                                :total="pageTotal"
                                @size-change="handleSizeChange"
                                @current-change="handlePageChange"
                        ></el-pagination>
                    </el-row>
                </div>
            </el-col>
        </el-row>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { fetchGoodsList } from '../../../api/goods'
import {Format} from '../../../common/dateFormat'
import bus from '../../common/bus'

export default {
    name: 'goodsList',
    data() {
        return {
            query: {
                name: '',
                pageNo: 1,
                pageSize: 20
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
            data: [{
                label: '中国',
                children: [
                    {
                        label: '广东省',
                        children: [
                            {
                                label: '广州市'
                            },{
                                label: '深圳市'
                            }
                        ]
                    },
                    {
                        label: '江西省',
                        children: [
                            {
                                label: '九江市'
                            },{
                                label: '南昌市'
                            }
                        ]
                    }
                ]
            }],
            defaultProps: {
                children: 'children',
                label: 'label'
            }
        };
    },
    created() {
        this.getData();
    },
    methods: {
        handleNodeClick(data) {
            console.log(data);
        },
        /**
         * 时间格式化
         * @param time
         * @returns {string|*}
         */
        renderTime(time) {
            Date.prototype.Format = Format;
            if(time){
                time = time.toString();
                if(time.length === 10){
                    time = (time-0) * 1000;
                }
                time = (time - 0);
                if (time) {
                    return (new Date(time)).Format("yyyy-MM-dd hh:mm:ss");
                } else {
                    return "--";
                }
            }
        },
        /**
         * 获取列表
         */
        getData() {
            fetchGoodsList(this.query).then(res => {
                console.log(res);
                this.tableData = res.data.records;
                this.pageTotal = res.data.total || 50;
            });
        },
        addUser(){
            console.log(this.$router);
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, 'pageNo', 1);
            this.getData();
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$message.success('删除成功');
                    this.tableData.splice(index, 1);
                })
                .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            this.$set(this.tableData, this.idx, this.form);
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageNo', val);
            this.getData();
        },
        handleSizeChange(val) {
            this.$set(this.query, 'pageSize', val);
            this.getData();
        },
    }
};
</script>

<style scoped>

.content-wrapper{
    height:100%;
    overflow-y: hidden;
}

.content-row{
    height: 100%;
}

.height-init{
    height: 100%;
}

.container{
    height: 100%;
}

.handle-box {
    height: 6.5%;
}

.handle-input {
    width: 120px;
    display: inline-block;
}

.list-table-row{
    height:83%;
}

.table {
    width: 100%;
    height: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
.pagination-self{
    height:10%;
    text-align: right;
    margin-top: 5px;
}
</style>
