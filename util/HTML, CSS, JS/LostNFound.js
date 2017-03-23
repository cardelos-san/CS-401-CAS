function myFunction() {
    var table = document.getElementById("body");
    var row = table.insertRow(0);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    cell1.index = "NEW CELL1";
    cell2.index = "NEW CELL2";
}