const json = { "columns": { "elements": [{ "javaType": "java.lang.String", "alias": "street" }, { "javaType": "java.lang.String", "alias": "city" }] }, "data": [["Laddringsweg 8", "Essen"], ["Laddringsweg 8", "Essen"], ["Laddringsweg 8", "Essen"]] };
const columns = json.columns.elements.map(it => it.alias + ":" + it.javaType);
const data = json.data;
console.log(columns);
console.table(data);
