var app = angular.module('app', []);
app.controller('ctrl', function ($scope) {

    $scope.people = [];
    $scope.btnAdd = function () { return $scope.people.push({ name: '', age: 0, id: -1 }); };
    $scope.btnRemove = function (i) {
        android.Remove(i.id);
        $scope.people.splice($scope.people.indexOf(i), 1);
    };
    $scope.btnSave = function (i) {
        if(i.id===-1){
            android.Add(i.name,i.age);
        }else
            android.Save(i.id,i.name,i.age);
        android.Msg("Saving Done");
    };
    $scope.GetAll=function (){
        var x=android.GetAll();
        $scope.people = JSON.parse(x);
    }
    $scope.GetAll();
});
//# sourceMappingURL=appCRUD.js.map