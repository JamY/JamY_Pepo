var Parser = require('./dom-parser.js');
function parseXML(xmlDate) {
    var XMLParser = new Parser.DOMParser();
    var doc = XMLParser.parseFromString(xmlDate);
    // var date1 = doc.getElementsByTagName("string")[0].firstChild.nodeValue;
    var date = doc.getElementsByTagName("string");
    var array = new Array(date.length);
    for(var i = 0; i < date.length; i++){
      array[i] = date[i].firstChild.nodeValue;
    }
    return array;
}

module.exports.parseXML = parseXML;