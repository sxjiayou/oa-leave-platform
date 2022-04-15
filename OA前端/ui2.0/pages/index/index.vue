<template>
	<view>

		<view class="u-flex user-box u-p-l-30 u-p-r-20 u-p-b-30">
			<view class="u-m-r-10">
				<u-avatar :src="pic" size="140"></u-avatar>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-20">{{name}}</view>
				<view class="u-font-14 u-tips-color">部门:{{departmentName}} 职位:{{title}}</view>
			</view>
			<view class="u-m-l-10 u-p-10">
				<u-icon name="scan" color="#969799" size="28"></u-icon>
			</view>
			<view class="u-m-l-10 u-p-10">
				<u-icon name="arrow-right" color="#969799" size="28"></u-icon>
			</view>
			<!-- //跳转登录窗口 -->
			<view class="btn-area" v-if="login===false">
				<navigator url="/pages/auth/login" open-type="redirect" hover-class="other-navigator-hover">登录</button>
				</navigator>
			</view>
			<!-- 注销账户 -->
			<view class="btn-area" v-else="login===true">
				<view @click="logout">注销</view>
			</view>

		</view>




		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="setting" title="设置"></u-cell-item>
			</u-cell-group>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				pic: 'https://uviewui.com/common/logo.png',
				show: true,
				//登录标记
				login: false,
				// 部门
				departmentName: '未登录',
				//名字
				name: '未登录',
				title: ''
			}
		},
		onShow() {

			if (uni.getStorageSync("login_flag") == true) {
				const userINfo = this.$myRequest({
					url: '/getUserInfo',
					header: {
						'cookie': `JSESSIONID=${uni.getStorageSync("session_key")}`
					}
				}).then((res) => {
					this.name = res.data.data.name
					this.departmentName = res.data.data.departmentName
					this.title = res.data.data.title
					this.login = true;
					uni.setStorageSync("name_key", this.name)
					uni.setStorageSync("departmentName_key", this.departmentName)
				})
			}

		},
		methods: {

			//注销
			logout() {
				this.$myRequest({
					url: '/logout',
					header: {
						'cookie': `JSESSIONID=${uni.getStorageSync("session_key")}`
					}
				}).then((res) => {
					uni.showToast({
						title: '注销成功'
					})
					//清除缓存
					uni.clearStorage();
					this.name = '未登录'
					this.departmentName = '未登录'
					this.login = false;
				})
			}

		}
	}
</script>

<style lang="scss">
	page {
		background-color: #ededed;
	}

	.camera {
		width: 54px;
		height: 44px;

		&:active {
			background-color: #ededed;
		}
	}

	.user-box {
		background-color: #fff;
	}
</style>
