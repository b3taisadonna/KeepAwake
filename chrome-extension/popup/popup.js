// KeepAwake Chrome Extension - Popup Script

// State
let isActive = false;
let wakeLock = null;
let alarmId = null;

// DOM Elements
const statusElement = document.getElementById('status');
const statusIcon = document.getElementById('status-icon');
const statusText = document.getElementById('status-text');
const toggleBtn = document.getElementById('toggleBtn');
const stopBtn = document.getElementById('stopBtn');
const timeoutInput = document.getElementById('timeout');
const showNotificationCheckbox = document.getElementById('showNotification');
const installAndroidAppLink = document.getElementById('installAndroidApp');

// Initialize
chrome.storage.local.get(['isActive', 'timeout', 'showNotification'], (result) => {
    isActive = result.isActive || false;
    timeoutInput.value = result.timeout !== undefined ? result.timeout : 0;
    showNotificationCheckbox.checked = result.showNotification !== false;
    updateUI();
});

// Event Listeners
toggleBtn.addEventListener('click', () => {
    if (!isActive) {
        startKeepAwake();
    }
});

stopBtn.addEventListener('click', () => {
    if (isActive) {
        stopKeepAwake();
    }
});

installAndroidAppLink.addEventListener('click', (e) => {
    e.preventDefault();
    // Open Android app install page
    chrome.tabs.create({ url: 'https://play.google.com/store/apps/details?id=com.example.keepawake' });
});

// Functions
function startKeepAwake() {
    // Try to use Screen Wake Lock API (experimental)
    if ('wakeLock' in navigator) {
        try {
            navigator.wakeLock.request('screen').then((lock) => {
                wakeLock = lock;
                isActive = true;
                saveState();
                updateUI();
                
                // Set alarm for timeout
                setTimeoutAlarm();
                
                // Show notification
                if (showNotificationCheckbox.checked) {
                    showNotification('KeepAwake Active', 'Screen will stay awake');
                }
            }).catch((error) => {
                console.error('Wake Lock API error:', error);
                // Fallback: Use alarm to periodically request wake lock
                fallbackKeepAwake();
            });
        } catch (error) {
            console.error('Wake Lock API not available:', error);
            fallbackKeepAwake();
        }
    } else {
        // Wake Lock API not available, use fallback
        fallbackKeepAwake();
    }
}

function fallbackKeepAwake() {
    // Fallback: Use a tab that requests wake lock
    chrome.tabs.create({ 
        url: chrome.runtime.getURL('keepawake.html') 
    }, (tab) => {
        isActive = true;
        saveState();
        updateUI();
        
        // Set alarm for timeout
        setTimeoutAlarm();
        
        // Show notification
        if (showNotificationCheckbox.checked) {
            showNotification('KeepAwake Active', 'Screen will stay awake (fallback mode)');
        }
    });
}

function stopKeepAwake() {
    // Release wake lock
    if (wakeLock) {
        wakeLock.release().then(() => {
            wakeLock = null;
        });
    }
    
    // Clear alarm
    if (alarmId) {
        chrome.alarms.clear(alarmId);
        alarmId = null;
    }
    
    // Close keepawake tab
    chrome.tabs.query({ url: chrome.runtime.getURL('keepawake.html') }, (tabs) => {
        tabs.forEach(tab => {
            chrome.tabs.remove(tab.id);
        });
    });
    
    isActive = false;
    saveState();
    updateUI();
    
    // Show notification
    if (showNotificationCheckbox.checked) {
        showNotification('KeepAwake Stopped', 'Screen can now turn off');
    }
}

function setTimeoutAlarm() {
    const timeout = parseInt(timeoutInput.value);
    
    if (timeout > 0) {
        // Clear existing alarm
        if (alarmId) {
            chrome.alarms.clear(alarmId);
        }
        
        // Set new alarm
        const delayInMinutes = timeout;
        alarmId = 'keepawake-timeout';
        
        chrome.alarms.create(alarmId, {
            delayInMinutes: delayInMinutes,
            periodInMinutes: 0
        });
    }
}

function saveState() {
    chrome.storage.local.set({
        isActive: isActive,
        timeout: parseInt(timeoutInput.value),
        showNotification: showNotificationCheckbox.checked
    });
}

function updateUI() {
    if (isActive) {
        statusElement.classList.add('active');
        statusElement.classList.remove('inactive');
        statusIcon.textContent = '🟢';
        statusText.textContent = 'ON';
        toggleBtn.textContent = 'Start';
        toggleBtn.disabled = true;
        stopBtn.disabled = false;
    } else {
        statusElement.classList.add('inactive');
        statusElement.classList.remove('active');
        statusIcon.textContent = '🔘';
        statusText.textContent = 'OFF';
        toggleBtn.textContent = 'Start';
        toggleBtn.disabled = false;
        stopBtn.disabled = true;
    }
}

function showNotification(title, message) {
    chrome.notifications.create({
        type: 'basic',
        iconUrl: 'icons/icon48.png',
        title: title,
        message: message
    });
}
