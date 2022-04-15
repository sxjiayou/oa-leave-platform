<template>
	<view class="wrap">
		
		<u-time-line v-if="noticeList.length==0">
			<u-time-line-item nodeTop="2">
				<template v-slot:node>
					<view class="u-node" style="background: #19be6b;">
						<u-icon name="pushpin-fill" color="#fff" :size="24"></u-icon>
					</view>
				</template>
				<template v-slot:content>
					<view>
						<view class="u-order-title">通知:{{content}}</view>
						<view class="u-order-time">{{createTime}}</view>
					</view>
				</template>
			</u-time-line-item>
		</u-time-line>
		
		<u-time-line v-if="noticeList.length!=0">
			<u-time-line-item nodeTop="2" v-for="(item,index) in noticeList" :key="item.noticeId">
				<template v-slot:node>
					<view class="u-node" style="background: #19be6b;">
						<u-icon name="pushpin-fill" color="#fff" :size="24"></u-icon>
					</view>
				</template>
				<template v-slot:content>
					<view>
						<view class="u-order-title">通知:{{item.content}}</view>
						<view class="u-order-time">{{item.createTime}}</view>
					</view>
				</template>
			</u-time-line-item>
		</u-time-line>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				noticeList: [],
				content: '没有通知',
				
			}
		},
		onShow(){
			//登录就加载 获取通知
			if (uni.getStorageSync("login_flag")) {
				this.$myRequest({
					url: '/noticeList',
					header: {
						'cookie': `JSESSIONID=${uni.getStorageSync("session_key")}`
					}
				}).then((res) => {
					//提取申请表信息
					this.noticeList = res.data.data.records
					console.log(this.noticeList)
				})
			}else{
				console.log('清空')
				this.noticeList =[]
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="scss" scoped>
	.wrap {
		padding: 24rpx 24rpx 24rpx 40rpx;
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
