module.exports = {
    devServer: {
        port: 80,
        proxy: {
            '/api': {
                target: 'http://ec2-3-134-79-22.us-east-2.compute.amazonaws.com:8080',
                ws: true,
                changeOrigin: true
            }
        }
    }
}