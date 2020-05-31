<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="品名" prop="name">
      <el-input v-model="dataForm.name" placeholder="品名"></el-input>
    </el-form-item>
    <el-form-item label="归属的店的主键" prop="shopid">
      <el-input v-model="dataForm.shopid" placeholder="归属的店的主键"></el-input>
    </el-form-item>
    <el-form-item label="归属的点名 冗余" prop="shop">
      <el-input v-model="dataForm.shop" placeholder="归属的点名 冗余"></el-input>
    </el-form-item>
    <el-form-item label=" 价格" prop="price">
      <el-input v-model="dataForm.price" placeholder=" 价格"></el-input>
    </el-form-item>
    <el-form-item label="分数" prop="score">
      <el-input v-model="dataForm.score" placeholder="分数"></el-input>
    </el-form-item>
    <el-form-item label="被收藏数" prop="collectnum">
      <el-input v-model="dataForm.collectnum" placeholder="被收藏数"></el-input>
    </el-form-item>
    <el-form-item label=" 菜品描述" prop="descr">
      <el-input v-model="dataForm.descr" placeholder=" 菜品描述"></el-input>
    </el-form-item>
    <el-form-item label="发布日期" prop="publishdate">
      <el-input v-model="dataForm.publishdate" placeholder="发布日期"></el-input>
    </el-form-item>
    <el-form-item label="更新日期" prop="upadatedate">
      <el-input v-model="dataForm.upadatedate" placeholder="更新日期"></el-input>
    </el-form-item>
    <el-form-item label="菜品被浏览的数量" prop="view">
      <el-input v-model="dataForm.view" placeholder="菜品被浏览的数量"></el-input>
    </el-form-item>
    <el-form-item label="菜品的宣传图用逗号隔开" prop="images">
      <el-input v-model="dataForm.images" placeholder="菜品的宣传图用逗号隔开"></el-input>
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
          name: '',
          shopid: '',
          shop: '',
          price: '',
          score: '',
          collectnum: '',
          descr: '',
          publishdate: '',
          upadatedate: '',
          view: '',
          images: ''
        },
        dataRule: {
          name: [
            { required: true, message: '品名不能为空', trigger: 'blur' }
          ],
          shopid: [
            { required: true, message: '归属的店的主键不能为空', trigger: 'blur' }
          ],
          shop: [
            { required: true, message: '归属的点名 冗余不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: ' 价格不能为空', trigger: 'blur' }
          ],
          score: [
            { required: true, message: '分数不能为空', trigger: 'blur' }
          ],
          collectnum: [
            { required: true, message: '被收藏数不能为空', trigger: 'blur' }
          ],
          descr: [
            { required: true, message: ' 菜品描述不能为空', trigger: 'blur' }
          ],
          publishdate: [
            { required: true, message: '发布日期不能为空', trigger: 'blur' }
          ],
          upadatedate: [
            { required: true, message: '更新日期不能为空', trigger: 'blur' }
          ],
          view: [
            { required: true, message: '菜品被浏览的数量不能为空', trigger: 'blur' }
          ],
          images: [
            { required: true, message: '菜品的宣传图用逗号隔开不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/cateen/food/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.food.name
                this.dataForm.shopid = data.food.shopid
                this.dataForm.shop = data.food.shop
                this.dataForm.price = data.food.price
                this.dataForm.score = data.food.score
                this.dataForm.collectnum = data.food.collectnum
                this.dataForm.descr = data.food.descr
                this.dataForm.publishdate = data.food.publishdate
                this.dataForm.upadatedate = data.food.upadatedate
                this.dataForm.view = data.food.view
                this.dataForm.images = data.food.images
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
              url: this.$http.adornUrl(`/cateen/food/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'shopid': this.dataForm.shopid,
                'shop': this.dataForm.shop,
                'price': this.dataForm.price,
                'score': this.dataForm.score,
                'collectnum': this.dataForm.collectnum,
                'descr': this.dataForm.descr,
                'publishdate': this.dataForm.publishdate,
                'upadatedate': this.dataForm.upadatedate,
                'view': this.dataForm.view,
                'images': this.dataForm.images
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
