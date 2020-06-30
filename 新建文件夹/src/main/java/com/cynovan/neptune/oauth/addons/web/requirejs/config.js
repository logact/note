var cynovan = cynovan || {};

require.config({
    baseUrl: 'resource',
    urlArgs: function (id, url) {
        var args = 'v=' + cynovan.version;
        if (~url.indexOf('amap.com')) {
            args = ''
            return '';
        }
        return ((~url.indexOf('?')) ? '&' : '?') + args;
    },
    paths: {
        'css': 'web/requirejs/css',
        'echarts': 'web/echart/echarts',
        'mousetrap': 'web/lib/mousetrap/mousetrap',
        'moment': 'web/lib/moment/locale',
        'chosen': 'web/lib/chosen/chosen',
        'taggle': 'web/lib/taggle/taggle'
    },
    shim: {
        dropzone: {
            deps: ['css!web/lib/dropzone/dropzone']
        },
        moment: {
            deps: ['web/lib/moment/moment']
        },
        chosen: {
            deps: ['css!web/lib/chosen/chosen']
        },
        taggle: {
            deps: ['css!web/lib/taggle/taggle']
        }
    }
});
