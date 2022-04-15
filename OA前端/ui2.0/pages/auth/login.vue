<template>
	<view class="wrap">
		<view class="top"></view>
		<view class="content">
			<view class="title">欢迎登录OA请假</view>
			<input class="u-border-bottom" type="string" v-model="username" placeholder="请输入账号" />
			<input class="u-border-bottom" type="password" v-model="password" placeholder="请输入密码" />
			<button @tap="login" :style="[inputStyle]" class="getCaptcha">登陆</button>
			
			<view class="alternative">
				<view class="password">找回密码</view>
				<view class="issue">注册</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				username: '',
				password: '',
				
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if (this.username && this.password) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['warning'];
				}
				return style;
			}
		},
		methods: {
			//发送登录请求
			async login() {
				console.log(this.username,this.password)
				const res = await this.$myRequest({
					url: '/login',
					data: {
						username: this.username,
						password: this.password
					},
					header: {
						'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
					},
					method: 'POST',
				})
					//登录成功
				if(res.data.code===2000){
					//打角色信息存储
					
					// header: {'cookie': uni.getStorageSync("session_key")},
					uni.setStorageSync('session_key', res.data.data);
					uni.setStorageSync('login_flag', true);
					console.info(uni.getStorageSync("session_key"))
					
					uni.showToast({
						title: '登录成功'
					})
					//页面转跳
					uni.switchTab({
					    url: '../index/index'
					});
				}
				
			}
		}
	};
</script>

<style lang="scss" scoped>
	.u-border-bottom {
		margin-bottom: 40rpx !important;
	}

	.wrap {
		font-size: 28rpx;

		.content {
			width: 600rpx;
			margin: 80rpx auto 0;

			.title {
				text-align: left;
				font-size: 60rpx;
				font-weight: 500;
				margin-bottom: 100rpx;
			}

			input {
				text-align: left;
				margin-bottom: 10rpx;
				padding-bottom: 6rpx;
			}

			.tips {
				color: $u-type-info;
				margin-bottom: 60rpx;
				margin-top: 8rpx;
			}

			.getCaptcha {
				background-color: rgb(253, 243, 208);
				color: $u-tips-color;
				border: none;
				font-size: 30rpx;
				padding: 12rpx 0;

				&::after {
					border: none;
				}
			}

			.alternative {
				color: $u-tips-color;
				display: flex;
				justify-content: space-between;
				margin-top: 30rpx;
			}
		}

		.buttom {
			.loginType {
				display: flex;
				padding: 350rpx 150rpx 150rpx 150rpx;
				justify-content: space-between;

				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					color: $u-content-color;
					font-size: 28rpx;
				}
			}

			.hint {
				padding: 20rpx 40rpx;
				font-size: 20rpx;
				color: $u-tips-color;

				.link {
					color: $u-type-warning;
				}
			}
		}
	}
</style>
