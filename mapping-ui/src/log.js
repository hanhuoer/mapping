export let log = {
    debug: null,
    info: null,
    warn: null,
    error: null,
};

let env = process.env.NODE_ENV;

log.debug = function (message) {
    if ("development" === env) {
        console.log(`%c${new Date().toISOString()} --- DEBUG: ${message}`, "color: #5c962c");
    }
};

log.info = function (message) {
    if ("development" === env) {
        console.log(`%c${new Date().toISOString()} ---  INFO: ${message}`, "color: #000000");
    }
};

log.warn = function (message) {
    if ("development" === env) {
        console.log(`%c${new Date().toISOString()} ---  WARN: ${message}`, "color: #a68a0d");
    }
};

log.error = function (message) {
    if ("development" === env) {
        console.log(`%c${new Date().toISOString()} --- ERROR: ${message}`, "color: #f0524f");
    }
};
