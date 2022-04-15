<template>
	<view class="wrap">
		<u-form :model="model" :rules="rules" ref="uForm" :errorType="errorType">
			<u-form-item :label-position="labelPosition" label="姓名" prop="name" label-width="150">
				{{model.name}}
			</u-form-item>

			<u-form-item :label-position="labelPosition" label="部门" prop="college" label-width="150">
				{{model.college}}
			</u-form-item>


			<u-form-item :label-position="labelPosition" label="请假类型" prop="cause" label-width="200">
				<u-radio-group v-model="radio" @change="radioGroupChange" :width="radioCheckWidth"
					:wrap="radioCheckWrap">
					<u-radio shape="circle" v-for="(item, index) in radioList" :key="index" :name="item.value">
						{{ item.name }}
					</u-radio>
				</u-radio-group>
			</u-form-item>

			<u-form-item :label-position="labelPosition" label="原因说明" prop="othercause" label-width="200">
				<u-input type="textarea" class="mar" :border="border" placeholder="请填写原因详情"
					v-model="model.othercause" />
			</u-form-item>


			<u-form-item :label-position="labelPosition" label="请假开始时间" prop="time" class="wrap">

				<u-picker mode="time" v-model="show1" :params="params" @confirm="confirm1" @click="show1 = true">
				</u-picker>
				<u-input type="button" :value="starinput" class="button ,mar" @click="show1 = true"
					placeholder="请选择请假开始时间">
				</u-input>
			</u-form-item>

			<u-form-item :label-position="labelPosition" label="请假结束时间" prop="time" class="wrap">
				<u-picker mode="time" v-model="show2" :params="params" @confirm="confirm2"></u-picker>
				<u-input type="button" :value="endinput" class="button ,mar" @click="show2 = true"
					placeholder="请选择请假结束时间"></u-input>
			</u-form-item>

		</u-form>
		<view class="agreement">
			<u-checkbox v-model="check" @change="checkboxChange"></u-checkbox>
			<view class="agreement-text">
				勾选代表确认填写的信息
			</view>
		</view>
		<u-button @click="submit">提交</u-button>
		<u-action-sheet :list="actionSheetList" v-model="actionSheetShow" @click="actionSheetCallback"></u-action-sheet>
		<u-select mode="single-column" :list="selectList" v-model="selectShow" @confirm="selectConfirm"></u-select>
		<u-picker mode="region" v-model="pickerShow" @confirm="regionConfirm"></u-picker>
		<u-verification-code seconds="60" ref="uCode" @change="codeChange"></u-verification-code>
		<view class="u-config-wrap">


		</view>
	</view>
</template>

<script>
	export default {
		data() {
			let that = this;
			return {
				show1: false,
				show2: false,
				mode: 'range',
				result: "",
				// 时间
				starinput: "",
				endinput: "",
				rangeColor: '#2979ff',
				rangeBgColor: 'rgba(41,121,255,0.13)',
				activeBgColor: '#2979ff',
				btnType: 'primary',

				params: {
					year: true,
					month: true,
					day: true,
					hour: true,
					minute: true,
					second: true,
					show: true
				},

				model: {
					name: '',
					sex: '',
					college: '',
					number: '',
					othercause: '',
					cause: '1',
					agreement: false,
				},
				rules: {
					name: [{
							required: true,
							message: '请输入姓名',
							trigger: 'blur',
						},
						{
							min: 2,
							max: 5,
							message: '姓名长度在2到5个字符',
							trigger: ['change', 'blur'],
						},
						{
							// 此为同步验证，可以直接返回true或者false，如果是异步验证，稍微不同，见下方说明
							validator: (rule, value, callback) => {
								// 调用uView自带的js验证规则，详见：https://www.uviewui.com/js/test.html
								return this.$u.test.chinese(value);
							},
							message: '姓名必须为中文',
							// 触发器可以同时用blur和change，二者之间用英文逗号隔开
							trigger: ['change', 'blur'],
						},
						// 异步验证，用途：比如用户注册时输入完账号，后端检查账号是否已存在
						// {
						// 	trigger: ['blur'],
						// 	// 异步验证需要通过调用callback()，并且在里面抛出new Error()
						// 	// 抛出的内容为需要提示的信息，和其他方式的message属性的提示一样
						// 	asyncValidator: (rule, value, callback) => {
						// 		this.$u.post('/ebapi/public_api/index').then(res => {
						// 			// 如果验证出错，需要在callback()抛出new Error('错误提示信息')
						// 			if(res.error) {
						// 				callback(new Error('姓名重复'));
						// 			} else {
						// 				// 如果没有错误，也要执行callback()回调
						// 				callback();
						// 			}
						// 		})
						// 	},
						// }
					],

					intro: [{
							required: true,
							message: '请填写原因'
						},
						{
							min: 10,
							message: '原因不能少于10个字',
							trigger: 'change',
						},
						// 正则校验示例，此处用正则校验是否中文，此处仅为示例，因为uView有this.$u.test.chinese可以判断是否中文
						{
							pattern: /^[\u4e00-\u9fa5]+$/gi,
							message: '简介只能为中文',
							trigger: 'change',
						},
					],

					cause: [{
						required: true,
						message: '请选择请假原因',
						trigger: 'change',
					}]

				},
				border: false,
				check: false,
				selectStatus: 'close',
				radioList: [{
						name: '病假',
						checked: true,
						disabled: false,
						value: '1'
					},
					{
						name: '事假',
						checked: false,
						disabled: false,
						value: '2'
					},
					{
						name: '其他原因',
						checked: false,
						disabled: false,
						value: '3'
					},

				],
				radio: '病假',
				actionSheetList: [{
						text: '男'
					},
					{
						text: '女'
					}

				],
				actionSheetShow: false,
				pickerShow: false,
				selectShow: false,
				radioCheckWidth: 'auto',
				radioCheckWrap: false,
				labelPosition: 'left',
				codeTips: '',
				errorType: ['message'],
			};
		},
		onShow() {
			//自动获取name 和部门名
			this.model.name = uni.getStorageSync("name_key")
			this.model.college = uni.getStorageSync("departmentName_key")
		},
		computed: {
			borderCurrent() {
				return this.border ? 0 : 1;
			},

		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {

			/**
		styleChange() {
			this.startText = '开始';
			this.endText = '结束';
			this.activeBgColor = '#2979ff';
			this.rangeColor = '#2979ff';
			this.rangeBgColor = 'rgba(41,121,255,0.13)';
			this.btnType = 'primary';
			this.show = true;
			},
			modeChange() {
				this.mode =  'range';
				this.show = true;
			},
			
			customChange() {
				this.icon1 = 'map';
				this.icon2 = 'photo';
				this.arrow = true;
				
				},
				textareaChange() {
					this.type = 'textarea';
				},
	change(e) {
		if (this.mode == 'range') {
			this.result = e.startDate + " - " + e.endDate;
		} else {
			this.result = e.result;
		}
	},
		*/
			confirm1(e) {
				console.log(e);
				this.starinput = '';
				if (this.params.year) this.starinput += e.year;
				if (this.params.month) this.starinput += '-' + e.month;
				if (this.params.day) this.starinput += '-' + e.day;
				if (this.params.hour) this.starinput += ' ' + e.hour;
				if (this.params.minute) this.starinput += ':' + e.minute;
				if (this.params.second) this.starinput += ':' + e.second;
			},
			confirm2(e) {
				console.log(e);
				this.endinput = '';
				if (this.params.year) this.endinput += e.year;
				if (this.params.month) this.endinput += '-' + e.month;
				if (this.params.day) this.endinput += '-' + e.day;
				if (this.params.hour) this.endinput += ' ' + e.hour;
				if (this.params.minute) this.endinput += ':' + e.minute;
				if (this.params.second) this.endinput += ':' + e.second;
			},

			submit() {
				this.$refs.uForm.validate(valid => {
					if (valid) {
						if (!this.model.agreement) return this.$u.toast('请勾选协议');
						console.log('验证通过');
						//调用接口
						console.log(this.starinput, this.endinput, this.model.othercause, this.model.cause)
						if (uni.getStorageSync("login_flag")) {
							this.$myRequest({
								url: '/leave/create',
								method: 'POST',
								data: {
									formType: this.model.cause,
									strStartTime: this.starinput,
									strEndTime: this.endinput,
									reason: this.model.othercause
								},
								header: {
									'cookie': `JSESSIONID=${uni.getStorageSync("session_key")}`,
									'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
								}
							}).then((res) => {
								//提取申请表信息
								console.info(res)
								uni.showToast({
									title: '提交成功'
								})
							})
						} else {
							uni.showToast({
								title: '未登录'
							})
						}


					} else {
						console.log('验证失败');
						uni.showToast({
							title: '验证失败'
						})
					}
				});
			},
			// 点击actionSheet回调
			actionSheetCallback(index) {
				uni.hideKeyboard();
				this.model.sex = this.actionSheetList[index].text;
			},

			// radio选择发生变化
			//请假原因
			radioGroupChange(e) {
				this.model.cause = e;
			},
			// 勾选版权协议
			checkboxChange(e) {
				this.model.agreement = e.value;
			},

			// 选择部门回调
			selectConfirm(e) {
				this.model.college = '';
				e.map((val, index) => {
					this.model.college += this.model.college == '' ? val.label : '-' + val.label;
				})
			},
			borderChange(index) {
				this.border = !index;
			},
			radioCheckboxChange(index) {
				if (index == 0) {
					this.radioCheckWrap = false;
					this.radioCheckWidth = 'auto';
				} else if (index == 1) {
					this.radioCheckWrap = true;
					this.radioCheckWidth = 'auto';
				} else if (index == 2) {
					this.radioCheckWrap = false;
					this.radioCheckWidth = '50%';
				}
			},
			labelPositionChange(index) {
				this.labelPosition = index == 'left';
			},
			codeChange(text) {
				this.codeTips = text;
			},
			errorChange(index) {
				if (index == 0) this.errorType = ['message'];
				if (index == 1) this.errorType = ['toast'];
				if (index == 2) this.errorType = ['border-bottom'];
				if (index == 3) this.errorType = ['border'];
			}
		}
	};
</script>

<style scoped lang="scss">
	.wrap {
		padding: 30rpx;
	}

	.agreement {
		display: flex;
		align-items: center;
		margin: 40rpx 0;

		.agreement-text {
			padding-left: 8rpx;
			color: $u-tips-color;

		}
	}
</style>
<style scoped lang="scss">
	.wrap {
		padding: 30rpx;
	}

	.mar {
		margin: 30rpx;
		text-align: center;
	}

	.agreement {
		display: flex;
		align-items: center;
		margin: 40rpx 0;

		.agreement-text {
			padding-left: 8rpx;
			color: $u-tips-color;
		}
	}

	.button {
		margin-right: 5rpx;

	}
</style>
