function HashMap(){
    this.clear = hashmap_clear;
    this.containsKey = hashmap_containsKey;
    this.containsValue = hashmap_containsValue;
    this.get = hashmap_get;
    this.isEmpty = hashmap_isEmpty;
    this.keys = hashmap_keys;
    this.put = hashmap_put;
    this.remove = hashmap_remove;
    this.size = hashmap_size;
    this.toString = hashmap_toString;
    this.values = hashmap_values;
    this.hashmap = new Array();
}

function hashmap_clear(){
    this.hashmap = new Array();
}

function hashmap_containsKey(key){
    var exists = false;
    for (var i in this.hashmap) {
        if (i == key && this.hashmap[i] != null) {
            exists = true;
            break;
        }
    }
    return exists;
}

function hashmap_containsValue(value){
    var contains = false;
    if (value != null) {
        for (var i in this.hashmap) {
            if (this.hashmap[i] == value) {
                contains = true;
                break;
            }
        }
    }
    return contains;
}

function hashmap_get(key){
    return this.hashmap[key];
}

function hashmap_isEmpty(){
    return (parseInt(this.size()) == 0) ? true : false;
}

function hashmap_keys(){
    var keys = new Array();
    for (var i in this.hashmap) {
        if (this.hashmap[i] != null)
            keys.push(i);
    }
    return keys;
}

function hashmap_put(key, value){
    if (key == null || value == null) {
        throw "NullPointerException {" + key + "},{" + value + "}";
    }else{
        this.hashmap[key] = value;
    }
}

function hashmap_remove(key){
    var rtn = this.hashmap[key];
    this.hashmap[key] = null;
    return rtn;
}

function hashmap_size(){
    var size = 0;
    for (var i in this.hashmap) {
        if (this.hashmap[i] != null)
            size ++;
    }
    return size;
}

function hashmap_toString(){
    var result = "";
    for (var i in this.hashmap)
    {     
        if (this.hashmap[i] != null)
            result += "{" + i + "},{" + this.hashmap[i] + "}\n";  
    }
    return result;
}

function hashmap_values(){
    var values = new Array();
    for (var i in this.hashmap) {
        if (this.hashmap[i] != null)
            values.push(this.hashmap[i]);
    }
    return values;
}