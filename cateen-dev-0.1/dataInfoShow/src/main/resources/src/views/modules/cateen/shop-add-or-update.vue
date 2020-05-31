<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="收藏的数量" prop="collectednumber">
      <el-input v-model="dataForm.collectednumber" placeholder="收藏的数量"></el-input>
    </el-form-item>
    <el-form-item label="商店的描述" prop="descr">
      <el-input v-model="dataForm.descr" placeholder="商店的描述"></el-input>
    </el-form-item>
    <el-form-item label="商店的名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="商店的名称"></el-input>
    </el-form-item>
    <el-form-item label="商店的位置" prop="location">
      <el-input v-model="dataForm.location" placeholder="商店的位置"></el-input>
    </el-form-item>
    <el-form-item label="被浏览的数量" prop="view">
      <el-input v-model="dataForm.view" placeholder="被浏览的数量"></el-input>
    </el-form-item>
    <el-form-item label="评分" prop="rate">
      <el-input v-model="dataForm.rate" placeholder="评分"></el-input>
    </el-form-item>
    <el-form-item label="商店的宣传图用逗号隔开" prop="images">
      <el-input v-model="dataForm.images" placeholder="商店的宣传图用逗号隔开"></el-input>
    </el-form-item>
    <el-form-item label="" prop="shoperid">
      <el-input v-model="dataForm.shoperid" placeholder=""></el-input>
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
          collectednumber: '',
          descr: '',
          name: '',
          location: '',
          view: '',
          rate: '',
          images: '',
          shoperid: ''
        },
        dataRule: {
          collectednumber: [
            { required: true, message: '收藏的数量不能为空', trigger: 'blur' }
          ],
          descr: [
            { required: true, message: '商店的描述不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '商店的名称不能为空', trigger: 'blur' }
          ],
          location: [
            { required: true, message: '商店的位置不能为空', trigger: 'blur' }
          ],
          view: [
            { required: true, message: '被浏览的数量不能为空', trigger: 'blur' }
          ],
          rate: [
            { required: true, message: '评分不能为空', trigger: 'blur' }
          ],
          images: [
            { required: true, message: '商店的宣传图用逗号隔开不能为空', trigger: 'blur' }
          ],
          shoperid: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/cateen/shop/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.collectednumber = data.shop.collectednumber
                this.dataForm.descr = data.shop.descr
                this.dataForm.name = data.shop.name
                this.dataForm.location = data.shop.location
                this.dataForm.view = data.shop.view
                this.dataForm.rate = data.shop.rate
                this.dataForm.images = data.shop.images
                this.dataForm.shoperid = data.shop.shoperid
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
              url: this.$http.adornUrl(`/cateen/shop/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'collectednumber': this.dataForm.collectednumber,
                'descr': this.dataForm.descr,
                'name': this.dataForm.name,
                'location': this.dataForm.location,
                'view': this.dataForm.view,
                'rate': this.dataForm.rate,
                'images': this.dataForm.images,
                'shoperid': this.dataForm.shoperid
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
