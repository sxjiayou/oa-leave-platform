<template>
	<view>
		<view class="wrap">
			<view class="top"></view>
			<view class="content">
				<view class="title">注册OA请假</view>
				
			    <u-form-item label="姓名" >
					<input class="u-border-bottom" type="password" v-model="name" placeholder="输入姓名" />
					</u-form-item>
				<u-form-item label="输入性别">
					<input class="u-border-bottom" type="password" v-model="sex" placeholder="输入性别" />
						</u-form-item>
				<u-form-item label="输入部门">
					<input class="u-border-bottom" type="password" v-model="college" placeholder="输入部门" />
			</u-form-item>
				<u-form-item label="输入邮箱">
					<input class="u-border-bottom" type="string"   v-model="username"   placeholder="输入邮箱" />
						</u-form-item>
				<u-form-item label="请输入密码">
					<input class="u-border-bottom" type="password" v-model="password"   placeholder="请输入密码" />
						</u-form-item>
				<u-form-item label="请确认密码">
					<input class="u-border-bottom" type="password" v-model="repassword" placeholder="确认密码" />
						</u-form-item>
			
					<button @tap="submit" :style="[inputStyle]" class="getCaptcha">注册</button>
			
			</view>
		
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				name:'',
				college:'',
				no:'',
				username:'',
				repassword:'',
				password:"",
				sex:'',
				labelPosition: 'left',
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if(this.username && this.password && this.repassword) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['warning'];
				}
				return style;
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="scss" scoped>
	.u-border-bottom{
		margin-bottom: 40rpx !important;
	}
.wrap {
	font-size: 60rpx;
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
			justify-content:space-between;
			
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
