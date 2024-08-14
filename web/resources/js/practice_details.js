function updateGroupOptions() {
    var subjectId = document.getElementById("subject").value;
    var selection = document.getElementById("selection").value;
    fetch(`practice-details?action=groupOptions&subjectId=${subjectId}&selection=${selection}`)
        .then(response => response.json())
        .then(data => {
            var groupSelect = document.getElementById("group");
            groupSelect.innerHTML = '<option value="" disabled selected>All</option>';
            data.forEach(group => {
                var option = document.createElement("option");
                if (selection === "topic") {
                    option.value = group.topicId;
                    option.text = group.topicName;
                } else {
                    option.value = group.dimensionId;
                    option.text = group.dimensionName;
                }
                groupSelect.add(option);
            });
        });
}