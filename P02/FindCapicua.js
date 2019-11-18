FindCapicua = function() {
    var numbers = db.phones.find({},{"display": 1, "_id": 0}).toArray();
    non_capicua = []

    for (var i =0; i<numbers.length; i++){
        var number = numbers[i].display
        number = number.split("-")[1]


        if(number == number.split('').reverse().join('')){
            print(number)
        }

    }
}