<template>
	<view>

		<view class="wrap">
			<u-time-line v-if="apply.length==0">
				<u-time-line-item nodeTop="2">
					<template v-slot:node>
						<view class="u-node" style="background: #ffb813;">
							<u-icon name="pushpin-fill" color="#fff" :size="24"></u-icon>
						</view>
					</template>
					<template v-slot:content>
						<view>
							<view class="u-order-title">没有待审批的请假表单</view>
						</view>
					</template>
				</u-time-line-item>
			</u-time-line>

		</view>

		<view>

			<view class="content" v-for="(item,index) in apply" :key="item.form_id">
				<!--事件时间轴-->
				<view class="flowChart">

					<!--左侧轴-->
					<view class="flowChart-left">
						<!--虚线-->
						<view class="dashed"></view>
					</view>

					<!--右侧内容-->
					<view class="flowChart-right">
						<!--一个节点-->
						<view class="oneNode">

							<!--左侧小球拒绝-->
							<view>
								<!--弹出输入审核意见框-->
								<u-popup v-model="show1" mode="center" border-radius="14" width="600rpx" height="110px">
									<view>请填写拒绝审核意见</view>
									<input class="mar" :border="10" border-color="#000000" v-model="causenot"></input>

									<button type="success" style="width: 250rpx;height: 100rpx;"
										@click="not1(index)">确定</button>
								</u-popup>
								<!-- 调用 /leave/audit?formId=40&result=approved&reason=ok -->
								<view class="check check-danger" @click="yes1()">
									拒绝
								</view>
							</view>



							<!--左侧小球同意-->
							<view>
								<!--弹出输入审核意见框-->
								<u-popup v-model="show2" mode="center" border-radius="14" width="600rpx" height="110px">
									<view>请填写同意审核意见</view>
									<input class="mar" :border="10" border-color="#000000" v-model="causeyes"></input>

									<button type="success" style="width: 250rpx;height: 100rpx;"
										@click="not2(index)">确定</button>
								</u-popup>

								<view class="check check-success" @click="yes2()">
									<!-- 传 approved  -->
									同意
								</view>
							</view>





							<view class="tag-boder">
								<view class="tag">
								</view>
							</view>
							<!--右侧内容-->
							<view class="NodeDetail">
								<!--上-->
								<view class="NodeDetail-title">
									<!--头像-->
									<image src="/static/user.png"></image>
									<!--内容-->
									<view class="details">
										<h4>{{item.department_name}}</h4>
										<view>{{item.name}}</view>
									</view>
								</view>
								<!--中-->
								<view class="NodeDetail-content">
									<text class="badge">请假原因:</text>
									<view>{{item.reason}}</view>

								</view>
								<!--下-->
								<view style="margin-top: 10px;"></view>
								<view class="NodeDetail-content">
									<text class="badge">请假时间:</text>
									<view>{{item.start_time}}</view>
									<view>至</view>
									<view>{{item.end_time}}</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>


		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {

				apply: [],
				refused: 'refused',
				approved: 'approved',
				show1: false,
				show2: false,
				causenot: '',
				causeyse: '',
			}


		},
		onShow() {
			//登录就加载
			if (uni.getStorageSync("login_flag")) {
				this.$myRequest({
					url: '/leave/list',
					header: {
						'cookie': `JSESSIONID=${uni.getStorageSync("session_key")}`
					}
				}).then((res) => {
					//提取申请表信息
					this.apply = res.data.data
					console.log(this.apply)
				})
			} else {
				console.log('清空')
				this.apply = []
			}
		},
		methods: {



			//拒绝弹窗
			//弹窗拒绝里的确认
			not1(index) {
				//关闭弹窗
				this.show1 = false
				const formId = this.apply[index].form_id;
				console.log(formId)


				if (uni.getStorageSync("login_flag") == true) {
					const userINfo = this.$myRequest({
						url: '/leave/audit',
						data: {
							formId: formId,
							result: this.refused,
							reason: this.causenot
						},
						header: {
							'cookie': `JSESSIONID=${uni.getStorageSync("session_key")}`
						}
					}).then((res) => {
						if (res.data.code == 2000) {
							uni.showToast({
								title: '已拒绝'
							})


							uni.switchTab({
								url: '../examine/examine',
								success: (res) => {
									let page = getCurrentPages().pop();
									if (page == undefined || page == null) return;
									page.onShow();
								}

							})

						}

					})
				}
			},
			//拒绝小球，点击产生弹窗事件
			yes1(formId) {
				console.log(formId)
				this.show1 = true
			},

			//确认弹窗
			//确认弹窗里的确认
			not2(index) {
				this.show2 = false

				const formId = this.apply[index].form_id;
				console.log(formId)


				if (uni.getStorageSync("login_flag") == true) {
					const userINfo = this.$myRequest({
						url: '/leave/audit',
						data: {
							formId: formId,
							result: this.approved,
							reason: this.causenot
						},
						header: {
							'cookie': `JSESSIONID=${uni.getStorageSync("session_key")}`
						}
					}).then((res) => {
						if (res.data.code == 2000) {
							uni.showToast({
								title: '已同意'
							})

							//刷新页面
							uni.switchTab({
								url: '../examine/examine',
								success: (res) => {
									let page = getCurrentPages().pop();
									if (page == undefined || page == null) return;
									page.onShow();
								}

							})
						}

					})
				}
			},
			//确认呢小球，点击产生弹窗事件
			yes2() {
				this.show2 = true
			},



			//获取请假申请信息
			async getApply() {


				//v-for="(item,index) in apply" :key="item.form_id"

			}





		}
	}
</script>

<style>
	.mar {

		margin: 30rpx;
		text-align: center;
	}

	.wrap {
		padding: 24rpx 24rpx 24rpx 40rpx;
	}

	.flowChart {
		background-color: rgb(245, 245, 245);
		padding: 12px;
		display: flex;
	}

	.flowChart-left {

		width: 50px;
		flex-shrink: 0;
	}

	.flowChart .dashed {

		width: 0px;
		height: 100%;
		margin-left: 50%;
		border-left-style: dashed;
		border-left-color: darkgray;
		border-left-width: 2px;
	}

	.flowChart-right {
		flex-grow: 1;
	}

	.flowChart .oneNode {
		display: flex;
		align-items: center;
		width: 100%;
		margin-bottom: 14px;
	}

	.flowChart .check {
		width: 40px;
		height: 40px;
		border-radius: 50px;
		line-height: 40px;
		color: white;
		font-size: 12px;
		text-align: center;
		position: relative;
		left: -49px;
		flex-shrink: 0;
		background-color: rgb(125, 122, 117);
		border: 4px solid rgb(173, 168, 163);
	}

	.flowChart .check-success {
		margin-left: -80rpx;
		margin-top: -100rpx;
		background-color: rgb(87, 194, 64);
		border: 4px solid rgba(198, 228, 177, 0.9);
	}

	.flowChart .check-danger {
		list-style-type: ;
		margin-left: 10rp;
		margin-top: 200rpx;
		background-color: rgb(228, 108, 35);
		border: 4px solid rgb(250, 166, 122);
	}

	.flowChart .NodeDetail {
		background-color: white;
		margin-left: -60px;
		flex-grow: 1;
		border: 1px solid gainsboro;
		border-radius: 3px;
		padding: 14px;
		display: flex;
		flex-direction: column;
		box-shadow: 1px 1px 3px gainsboro;
	}

	.flowChart image {
		width: 40px;
		height: 40px;
		border-radius: 20px;
	}

	.NodeDetail .details {
		flex-grow: 1;
		padding-left: 8px;
		margin-left: 8px;
		border-left: 4px solid rgb(72, 108, 160);
	}

	.details view {
		color: rgb(154, 154, 154);
		font-size: 14px;
		font-weight: 100;
	}

	.NodeDetail-title {
		display: flex;
		margin-bottom: 10px;
	}

	.NodeDetail-content {
		flex-grow: 1;
	}

	.NodeDetail-content view {
		text-indent: 2em;
		text-align: justify;
		font-size: 14px;
	}

	.NodeDetail-footer {
		margin-top: 10px;
	}

	.NodeDetail-footer text {
		font-size: 12px;
		font-weight: 100;
		color: rgb(104, 104, 104);
		float: right;
	}

	.badge {

		padding: 2px 5px;
		font-size: 12px;
		background-color: rgb(72, 108, 160);
		border-radius: 10px;
		font-weight: 100;
		color: white;
		letter-spacing: 2px;
		box-shadow: 1px 1px 1px gainsboro;
	}

	.BadgeGray {
		background-color: rgb(104, 108, 104);
	}

	.tag {

		width: 0;
		height: 0;
		border: 14px solid;
		border-style: dashed;
		border-color: transparent white transparent transparent;
		position: absolute;
		left: -13px;
		top: -14px;
	}

	.tag-boder {

		width: 0;
		height: 0;
		border: 12px solid;
		border-style: dashed;
		border-color: transparent gainsboro transparent transparent;
		position: relative;
		left: -59px;
	}

	.NodeDetail-content view {

		margin-top: 7px;
	}

	.u-node {
		width: 44rpx;
		height: 44rpx;
		border-radius: 100rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		background: #d0d0d0;
	}

	.u-order-title {
		color: #333333;
		font-weight: bold;
		font-size: 32rpx;
	}

	.u-order-title.unacive {
		color: rgb(150, 150, 150);
	}

	.u-order-desc {
		color: rgb(150, 150, 150);
		font-size: 28rpx;
		margin-bottom: 6rpx;
	}

	.u-order-time {
		color: rgb(200, 200, 200);
		font-size: 26rpx;
	}

	.tel {
		color: $u-type-primary;
	}
</style>
