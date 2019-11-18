CountPrefix = function() {
    return db.phones.aggregate([{
        $group: {_id: '$components.prefix', count_phones: {$sum: 1}}
        
    }])
}
