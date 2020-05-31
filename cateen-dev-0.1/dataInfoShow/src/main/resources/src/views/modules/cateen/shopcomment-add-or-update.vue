<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="评论的内容给" prop="content">
      <el-input v-model="dataForm.content" placeholder="评论的内容给"></el-input>
    </el-form-item>
    <el-form-item label="评论所属的商店id" prop="shopid">
      <el-input v-model="dataForm.shopid" placeholder="评论所属的商店id"></el-input>
    </el-form-item>
    <el-form-item label="父评论id" prop="parentid">
      <el-input v-model="dataForm.parentid" placeholder="父评论id"></el-input>
    </el-form-item>
    <el-form-item label="发布者id" prop="frompublisherid">
      <el-input v-model="dataForm.frompublisherid" placeholder="发布者id"></el-input>
    </el-form-item>
    <el-form-item label="回复对象id" prop="topublisherid">
      <el-input v-model="dataForm.topublisherid" placeholder="回复对象id"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          content: '',
          shopid: '',
          parentid: '',
          frompublisherid: '',
          topublisherid: ''
        },
        dataRule: {
          content: [
            { required: true, message: '评论的内容给不能为空', trigger: 'blur' }
          ],
          shopid: [
            { required: true, message: '评论所属的商店id不能为空', trigger: 'blur' }
          ],
          parentid: [
            { required: true, message: '父评论id不能为空', trigger: 'blur' }
          ],
          frompublisherid: [
            { required: true, message: '发布者id不能为空', trigger: 'blur' }
          ],
          topublisherid: [
            { required: true, message: '回复对象id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/cateen/shopcomment/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.content = data.shopcomment.content
                this.dataForm.shopid = data.shopcomment.shopid
                this.dataForm.parentid = data.shopcomment.parentid
                this.dataForm.frompublisherid = data.shopcomment.frompublisherid
                this.dataForm.topublisherid = data.shopcomment.topublisherid
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/cateen/shopcomment/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'content': this.dataForm.content,
                'shopid': this.dataForm.shopid,
                'parentid': this.dataForm.parentid,
                'frompublisherid': this.dataForm.frompublisherid,
                'topublisherid': this.dataForm.topublisherid
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
