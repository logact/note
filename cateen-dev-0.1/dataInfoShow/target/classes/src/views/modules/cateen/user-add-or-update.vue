<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="学号" prop="cno">
      <el-input v-model="dataForm.cno" placeholder="学号"></el-input>
    </el-form-item>
    <el-form-item label="口味偏好用逗号隔开" prop="taste">
      <el-input v-model="dataForm.taste" placeholder="口味偏好用逗号隔开"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label="校区" prop="campus">
      <el-input v-model="dataForm.campus" placeholder="校区"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-input v-model="dataForm.gender" placeholder="性别"></el-input>
    </el-form-item>
    <el-form-item label="用户的姓名" prop="avatar">
      <el-input v-model="dataForm.avatar" placeholder="用户的姓名"></el-input>
    </el-form-item>
    <el-form-item label="用户的级别" prop="levelnum">
      <el-input v-model="dataForm.levelnum" placeholder="用户的级别"></el-input>
    </el-form-item>
    <el-form-item label="用户图片" prop="avatarimg">
      <el-input v-model="dataForm.avatarimg" placeholder="用户图片"></el-input>
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
          username: '',
          cno: '',
          taste: '',
          email: '',
          campus: '',
          gender: '',
          avatar: '',
          levelnum: '',
          avatarimg: ''
        },
        dataRule: {
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          cno: [
            { required: true, message: '学号不能为空', trigger: 'blur' }
          ],
          taste: [
            { required: true, message: '口味偏好用逗号隔开不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' }
          ],
          campus: [
            { required: true, message: '校区不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          avatar: [
            { required: true, message: '用户的姓名不能为空', trigger: 'blur' }
          ],
          levelnum: [
            { required: true, message: '用户的级别不能为空', trigger: 'blur' }
          ],
          avatarimg: [
            { required: true, message: '用户图片不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/cateen/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.username = data.user.username
                this.dataForm.cno = data.user.cno
                this.dataForm.taste = data.user.taste
                this.dataForm.email = data.user.email
                this.dataForm.campus = data.user.campus
                this.dataForm.gender = data.user.gender
                this.dataForm.avatar = data.user.avatar
                this.dataForm.levelnum = data.user.levelnum
                this.dataForm.avatarimg = data.user.avatarimg
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
              url: this.$http.adornUrl(`/cateen/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'username': this.dataForm.username,
                'cno': this.dataForm.cno,
                'taste': this.dataForm.taste,
                'email': this.dataForm.email,
                'campus': this.dataForm.campus,
                'gender': this.dataForm.gender,
                'avatar': this.dataForm.avatar,
                'levelnum': this.dataForm.levelnum,
                'avatarimg': this.dataForm.avatarimg
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
