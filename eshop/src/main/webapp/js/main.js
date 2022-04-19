function cart(message) {
    swal({
        title: message,        
        icon: "success",
        button: "Ok",
    });
}

function passSuccess(message) {
    swal({
        title: message,        
        icon: "success",
        button: "Ok",
    });
    
}

function editSuccess(message) {
    swal({
        title: message,        
        icon: "success",
        button: "Ok",
    });
    
}

function passFail(message) {
    swal({
        title: message,        
        icon: "error",
        button: "Ok",
    });
    
}

function editFail(message) {
    swal({
        title: message,        
        icon: "error",
        button: "Ok",
    });
    
}

function deletePreke(action) {
    swal({
        title: "Ar tikrai norite ištrinti?",
        text: "Ištrinto įrašo neįmanoma atkurti",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
            .then((willDelete) => {
                if (willDelete) {
                    window.location.href = action;
                    swal("Prekė ištrinta", {
                        icon: "success",
                    });
                } else {
                    swal("Prekė neištrinta");
                }
            });
}
function editPreke(action){
      window.location.href = action;
}

function toDelete(action) {
    swal({
        title: "Ar tikrai norite ištrinti?",
        text: "Ištrinto įrašo neįmanoma atkurti",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
            .then((willDelete) => {
                if (willDelete) {
                    window.location.href = action;
                    swal("Ištrinta", {
                        icon: "success",
                    });
                } else {
                    swal("Trynimas atšauktas");
                }
            });
}
function edit(action){
      window.location.href = action;
}

