<template>
  <div class="app-container">

    <el-row :gutter="20">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="统计方式" >
        <el-select
          v-model="queryParams.type"
          placeholder="统计方式"
          clearable
          size="small"
          style="width: 120px"
        >
          <el-option
            v-for="type in typeOptions"
            :key="type.key"
            :label="type.value"
            :value="type.key"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="创建时间" v-if="this.queryParams.type ==1">

            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
      </el-form-item>


        <el-form-item label="创建时间"  v-show="this.queryParams.type != 1">

          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 240px"
            type="monthrange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>


        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新增</el-button>
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
          >导出</el-button>

      <!--<el-form-item>-->
        <!--<button @click="playVoice">播放语音</button>-->

      <!--</el-form-item>-->
        </el-form>
      <div>
        <span style="color: red;font-size: 10px">单位：元</span>
      </div>
        <el-row :gutter="10" class="mb8">

          <!--<el-col :span="1.5">-->
            <!--<el-button-->
              <!--type="success"-->
              <!--icon="el-icon-edit"-->
              <!--size="mini"-->
              <!--:disabled="single"-->
              <!--@click="handleUpdate"-->
              <!--v-hasPermi="['system:user:edit']"-->
            <!--&gt;修改</el-button>-->
          <!--</el-col>-->
          <!--<el-col :span="1.5">-->
            <!--<el-button-->
              <!--type="danger"-->
              <!--icon="el-icon-delete"-->
              <!--size="mini"-->
              <!--:disabled="multiple"-->
              <!--@click="handleDelete"-->
              <!--v-hasPermi="['system:user:remove']"-->
            <!--&gt;删除</el-button>-->
          <!--</el-col>-->
          <!--<el-col :span="1.5">-->
            <!--<el-button-->
              <!--type="info"-->
              <!--icon="el-icon-upload2"-->
              <!--size="mini"-->
              <!--@click="handleImport"-->
              <!--v-hasPermi="['system:user:import']"-->
            <!--&gt;导入</el-button>-->
          <!--</el-col>-->
          <!--<el-col :span="1.5">-->
            <!--<el-button-->
              <!--type="warning"-->
              <!--icon="el-icon-download"-->
              <!--size="mini"-->
              <!--@click="handleExport"-->
              <!--v-hasPermi="['system:user:export']"-->
            <!--&gt;导出</el-button>-->
          <!--</el-col>-->
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="40" align="center" />
          <el-table-column label="编号" align="center" prop="id" />
          <el-table-column label="总金额" align="center" prop="amount" />
          <el-table-column label="买菜" align="center" prop="maicai" :show-overflow-tooltip="true" />
          <el-table-column label="水果" align="center" prop="shuiguo" :show-overflow-tooltip="true" />
          <el-table-column label="交通" align="center" prop="jiaotong" :show-overflow-tooltip="true" />
          <el-table-column label="逛街" align="center" prop="guangjie" :show-overflow-tooltip="true" />
          <el-table-column label="网购" align="center" prop="guangjie" :show-overflow-tooltip="true" />
          <el-table-column label="零食" align="center" prop="lingshi" :show-overflow-tooltip="true" />
          <el-table-column label="加餐" align="center" prop="jiacan" :show-overflow-tooltip="true" />
          <el-table-column label="请客" align="center" prop="qingke" :show-overflow-tooltip="true" />
          <el-table-column label="其他" align="center" prop="qita" :show-overflow-tooltip="true" />
          <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="180"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >修改</el-button>
              <el-button
                v-if="scope.row.userId !== 1"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >删除</el-button>

            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      <!--</el-col>-->
    </el-row>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px">
      <el-form ref="form" :model="form"  label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="买菜" prop="nickName">
              <el-input v-model="form.maicai" placeholder="请输入金额" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="水果" prop="deptId">
              <el-input v-model="form.shuiguo"  placeholder="请输入金额" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="交通" prop="phonenumber">
              <el-input v-model="form.jiaotong" placeholder="请输入金额" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="逛街" prop="email">
              <el-input v-model="form.guangjie" placeholder="请输入金额" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="网购" prop="userName">
              <el-input v-model="form.wanggou" placeholder="请输入金额" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="零食" prop="userName">
              <el-input v-model="form.lingshi" placeholder="请输入金额" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="加餐" prop="userName">
              <el-input v-model="form.jiacan" placeholder="请输入金额" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="请客" prop="userName">
              <el-input v-model="form.qingke" placeholder="请输入金额" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="其他" prop="userName">
              <el-input v-model="form.qita" placeholder="请输入金额" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, exportUser, resetUserPwd, changeUserStatus, importTemplate } from "@/api/system/user";
import {listZhoulExpense,addExpense,getExpenseDetail, deleteExpense } from "@/api/expense/zhoul";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

const synth = window.speechSynthesis;
const msg = new SpeechSynthesisUtterance();

export default {
  name: "Expense",
  components: { Treeselect },
  data() {
    return {
      typeOptions:[{"key":"1","value":"日"},{"key":"2","value":"月"},{"key":"3","value":"年"}],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      maicai: null,
      shuiguo: null,
      jiaotong: null,
      guangjie: null,
      wanggou :null,
      lingshi: null,
      jiacan: null,
      qingke: null,
      qita: null,

      remark: null,

      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // 查询参数
      queryParams: {
        type: null,
        start : null,
        end :null,
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },

    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.queryParams.type = this.typeOptions[0].key;
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {

    playVoice() {
      this.handleSpeak('小朋友，你是否有很多问号') // 传入需要播放的文字
    },
    // 语音播报的函数
    handleSpeak(text) {
      msg.text = text;     // 文字内容: 小朋友，你是否有很多问号
      msg.lang = "zh-CN";  // 使用的语言:中文
      msg.volume = 1;      // 声音音量：1
      msg.rate = 1;        // 语速：1
      msg.pitch = 1;       // 音高：1
      synth.speak(msg);    // 播放
    },
    // 语音停止
    handleStop(e) {
      msg.text = e;
      msg.lang = "zh-CN";
      synth.cancel(msg);
    },

    /** 查询用户列表 */
    getList() {
      this.loading = true;
      if (this.dateRange !=null){
        this.queryParams.start=this.dateRange[0];
        this.queryParams.end=this.dateRange[1];
      }
      listZhoulExpense(this.queryParams).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.userName + '"用户吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeUserStatus(row.userId, row.status);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {

      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      getUser().then(response => {
        this.open = true;
        this.title = "清单";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids;
      getExpenseDetail(id).then(response => {
        this.form = response.expenseDay;
        this.open = true;
        this.title = "修改";
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(({ value }) => {
          resetUserPwd(row.userId, value).then(response => {
            if (response.code === 200) {
              this.msgSuccess("修改成功，新密码是：" + value);
            } else {
              this.msgError(response.msg);
            }
          });
        }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm: function() {

      addExpense(this.form).then(response => {
        if (response.code === 200) {
        this.msgSuccess("新增成功");
        this.open = false;
        this.getList();
      } else {
        this.msgError(response.msg);
      }
    })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids;
      this.$confirm('是否确认删除用户编号为"' + id + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return deleteExpense(id);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUser(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
