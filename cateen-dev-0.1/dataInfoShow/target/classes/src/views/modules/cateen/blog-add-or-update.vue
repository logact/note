<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户的主键" prop="authorid">
      <el-input v-model="dataForm.authorid" placeholder="用户的主键"></el-input>
    </el-form-item>
    <el-form-item label="blog的图片" prop="img">
      <el-input v-model="dataForm.img" placeholder="blog的图片"></el-input>
    </el-form-item>
    <el-form-item label="发布的日期" prop="publish">
      <el-input v-model="dataForm.publish" placeholder="发布的日期"></el-input>
    </el-form-item>
    <el-form-item label="blog 的内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="blog 的内容"></el-input>
    </el-form-item>
    <el-form-item label="收藏的人数" prop="loveCount">
      <el-input v-model="dataForm.loveCount" placeholder="收藏的人数"></el-input>
    </el-form-item>
    <el-form-item label="浏览9的人数" prop="attentionCount">
      <el-input v-model="dataForm.attentionCount" placeholder="浏览9的人数"></el-input>
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
          authorid: '',
          img: '',
          publish: '',
          content: '',
          loveCount: '',
          attentionCount: ''
        },
        dataRule: {
          authorid: [
            { required: true, message: '用户的主键不能为空', trigger: 'blur' }
          ],
          img: [
            { required: true, message: 'blog的图片不能为空', trigger: 'blur' }
          ],
          publish: [
            { required: true, message: '发布的日期不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: 'blog 的内容不能为空', trigger: 'blur' }
          ],
          loveCount: [
            { required: true, message: '收藏的人数不能为空', trigger: 'blur' }
          ],
          attentionCount: [
            { required: true, message: '浏览9的人数不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/cateen/blog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.authorid = data.blog.authorid
                this.dataForm.img = data.blog.img
                this.dataForm.publish = data.blog.publish
                this.dataForm.content = data.blog.content
                this.dataForm.loveCount = data.blog.loveCount
                this.dataForm.attentionCount = data.blog.attentionCount
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
              url: this.$http.adornUrl(`/cateen/blog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'authorid': this.dataForm.authorid,
                'img': this.dataForm.img,
                'publish': this.dataForm.publish,
                'content': this.dataForm.content,
                'loveCount': this.dataForm.loveCount,
                'attentionCount': this.dataForm.attentionCount
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
