function update(userId, event) {

  event.preventDefault();
  let data = $("#profileUpdate").serialize();


  $.ajax({
    type: "put",
    url: "/api/user/" + userId,
    data: data,
    contentType: "application/x-www-form-urlencoded; charset=utf-8",
    dataType: "json",
  }).done((res) => {
    location.href = "/user/" + userId;
  });
}