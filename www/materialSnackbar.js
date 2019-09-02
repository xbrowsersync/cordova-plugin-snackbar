var snackbar = {
    create: function (text, duration, bgColor, textColor, maxLines, button, successCallback, errorCallback) {
    cordova.exec(
        successCallback,
        errorCallback,
        'MaterialSnackbar',
        'show',
        [{
        "text": text,
        "duration": duration || 0,
        "bgColor": bgColor || '',
        "textColor": textColor || '',
        "maxLines": maxLines || 2,
        "button": button || ''
        }]
    );
    },

    close: function (successCallback, errorCallback) {
    cordova.exec(
        successCallback,
        errorCallback,
        'MaterialSnackbar',
        'hide'
    );
    }
}

module.exports = snackbar;
