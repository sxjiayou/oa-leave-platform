const BASE_URL = 'http://175.24.112.136:8081'
// http://175.24.112.136:8081/login?username=m8&password=test登录接口

export const myRequest = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			header: options.header||{},
			success: (res) => {
				console.info(res)
				if (res.statusCode !== 200) {
					return uni.showToast({
						title: '获取数据失败'
					})
				}
				resolve(res)
			},
			fail: (err) => {
				uni.showToast({
					title: '请求接口失败'
				})
				reject(err)
			}
		})
	})

}
