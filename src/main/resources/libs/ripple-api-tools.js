
var message = null;
var methodMatch = {
    "setOption": createAPI,
    "preparePayment": preparePayment,
    "disconnect": disconnect,
    "submit": submit,
    "sign": sign
};
window.addEventListener('message', onMessage);


var api={};


function onMessage(event) {
    var method = event.data.method_name;
    var call_id = event.data.call_id;
    var parameters = (JSON.parse(event.data.parameters));
    var result = methodMatch[method](parameters);
    result.then(function (message) {
        console.log("message from api solved "+method);
        var toScala = {
                        call_id: event.data.call_id,
                        response: JSON.stringify(message)
                      };
        if(method == "setOption"){
            toScala.response = JSON.stringify({
                                                connected: true,
                                                info: "success"
                                              });
        }
        parent.postMessage(toScala, "*");
        }).catch(function (message) {
            console.log("exception caught");
            var toScala = {
                            call_id: event.data.call_id,
                            response: JSON.stringify(message)
                          };
            if(method == "setOption"){
              toScala.response = JSON.stringify({
                                                  connected: false,
                                                  info: "error"
                                                 });
            }
            parent.postMessage(toScala, "*");
        });
    }

function disconnect(){     //not used yet
    return api.disconnect();
}

function sign(parameters) {
    return new Promise((resolve, reject) => {
        resolve(api.sign(parameters));
    });
}

function submit(parameters) {
    return api.submit(parameters);
}

function preparePayment(parameters) {
    return api.preparePayment(parameters);
}

function createAPI(options){
    api = new RippleAPI(options);
    api.on('error', (errorCode, errorMessage) => {
      console.log('error api'+errorCode + ': ' + errorMessage);
    });
    api.on('connected', function () {
      console.log('connected');
    });
    api.on('disconnected', function (code) {
      // code - [close code](https://developer.mozilla.org/en-US/docs/Web/API/CloseEvent) sent by the server
      // will be 1000 if this was normal closure
      console.log('disconnected, code:', code);
    });
    api.on('ledger', ledger => {
      sendLedger(ledger);
    });
    api.on('error', (errorCode, errorMessage, data) => {
      console.log(errorCode + ': ' + errorMessage);
      sendError(error);
    });
    return api.connect();
}

function sendError(error) {
    var event = new ErrorEvent(error);
    parent.dispatchEvent(event);
}

function sendLedger(ledger) {
    var event = new CustomEvent(
        "newLedger",
        {
            detail: {
                message: "new Ledger",
                ledger: ledger
            },
            bubbles: true,
            cancelable: true
        }
                                );
    parent.dispatchEvent(event);
}