// 主线程代码
function parallelSum() {
    const workerCount = 4;
    const total = 10000;
    const chunkSize = Math.ceil(total / workerCount);
    const results = [];
    let completedWorkers = 0;
    let finalSum = 0;

    console.log('开始并行计算...');

    // 创建Web Worker的代码字符串
    const workerScript = `
        self.onmessage = function(e) {
            const { start, end } = e.data;
            let sum = 0;
            for (let i = start; i <= end; i++) {
                sum += i;
            }
            self.postMessage(sum);
        };
    `;

    // 创建Blob URL
    const blob = new Blob([workerScript], { type: 'application/javascript' });
    const workerUrl = URL.createObjectURL(blob);

    // 创建并启动所有worker
    for (let i = 0; i < workerCount; i++) {
        const start = i * chunkSize + 1;
        const end = Math.min((i + 1) * chunkSize, total);

        const worker = new Worker(workerUrl);

        worker.postMessage({ start, end });

        worker.onmessage = function(e) {
            results[i] = e.data;
            completedWorkers++;

            if (completedWorkers === workerCount) {
                finalSum = results.reduce((acc, val) => acc + val, 0);
                console.log(`1到10000的和是: ${finalSum}`);

                // 清理资源
                URL.revokeObjectURL(workerUrl);
            }
        };
    }
}

// 执行计算
parallelSum();