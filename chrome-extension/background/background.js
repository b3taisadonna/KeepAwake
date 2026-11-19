// KeepAwake Chrome Extension - Background Script

// Listen for alarm events
chrome.alarms.onAlarm.addListener((alarm) => {
    if (alarm.name === 'keepawake-timeout') {
        // Timeout reached, stop keep awake
        chrome.storage.local.get(['isActive'], (result) => {
            if (result.isActive) {
                // Send message to popup to stop
                chrome.runtime.sendMessage({ action: 'stopKeepAwake' });
            }
        });
    }
});

// Listen for messages from popup
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.action === 'startKeepAwake') {
        startKeepAwakeInBackground();
    } else if (request.action === 'stopKeepAwake') {
        stopKeepAwakeInBackground();
    }
});

// Handle tab updates
chrome.tabs.onUpdated.addListener((tabId, changeInfo, tab) => {
    if (changeInfo.url && changeInfo.url.includes('keepawake.html')) {
        // Inject script to request wake lock
        chrome.scripting.executeScript({
            target: { tabId: tabId },
            files: ['keepawake.js']
        });
    }
});

// Functions
function startKeepAwakeInBackground() {
    // Try to use Screen Wake Lock API in background
    // Note: This may not work in background, but we try
    chrome.storage.local.get(['timeout', 'showNotification'], (result) => {
        const timeout = parseInt(result.timeout) || 0;
        
        if (timeout > 0) {
            chrome.alarms.create('keepawake-timeout', {
                delayInMinutes: timeout,
                periodInMinutes: 0
            });
        }
    });
}

function stopKeepAwakeInBackground() {
    // Clear all alarms
    chrome.alarms.clearAll();
}

// Initialize
chrome.runtime.onInstalled.addListener(() => {
    // Set default values on first install
    chrome.storage.local.get(['isActive'], (result) => {
        if (result.isActive === undefined) {
            chrome.storage.local.set({
                isActive: false,
                timeout: 0,
                showNotification: true
            });
        }
    });
});
