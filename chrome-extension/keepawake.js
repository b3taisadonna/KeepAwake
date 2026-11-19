// KeepAwake - Wake Lock Page Script

let wakeLock = null;
let retryCount = 0;
const maxRetries = 5;

function requestWakeLock() {
    if ('wakeLock' in navigator) {
        navigator.wakeLock.request('screen')
            .then((lock) => {
                wakeLock = lock;
                document.getElementById('status').textContent = 'Wake lock acquired! Screen will stay awake.';
                console.log('Wake lock acquired');
                
                // Listen for wake lock release
                lock.addEventListener('release', () => {
                    document.getElementById('status').textContent = 'Wake lock released. Trying to reacquire...';
                    console.log('Wake lock released');
                    retryWakeLock();
                });
            })
            .catch((error) => {
                console.error('Failed to acquire wake lock:', error);
                document.getElementById('status').textContent = 'Failed to acquire wake lock: ' + error.message;
                retryWakeLock();
            });
    } else {
        document.getElementById('status').textContent = 'Wake Lock API not supported in this browser.';
        console.error('Wake Lock API not supported');
    }
}

function retryWakeLock() {
    retryCount++;
    if (retryCount < maxRetries) {
        setTimeout(() => {
            console.log('Retrying wake lock... Attempt ' + retryCount);
            requestWakeLock();
        }, 2000);
    } else {
        document.getElementById('status').textContent = 'Failed to acquire wake lock after multiple attempts.';
    }
}

// Start requesting wake lock
requestWakeLock();

// Clean up on page unload
window.addEventListener('beforeunload', () => {
    if (wakeLock) {
        wakeLock.release();
    }
});

// Check if page is visible and reacquire if needed
window.addEventListener('visibilitychange', () => {
    if (document.visibilityState === 'visible') {
        if (!wakeLock) {
            requestWakeLock();
        }
    }
});
