<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>Torrent</title>

    <link rel="stylesheet" type="text/css" href="css/torrent.css">
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="//cdn.datatables.net/plug-ins/725b2a2115b/integration/bootstrap/3/dataTables.bootstrap.css">

    <script type="text/javascript" language="javascript" src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="//cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="//cdn.datatables.net/plug-ins/725b2a2115b/integration/bootstrap/3/dataTables.bootstrap.js"></script>
    <script type="text/javascript" charset="utf-8">
        Date.prototype.toLocaleFormat = function(format) {
            var f = {y : this.getYear() + 1900,m : this.getMonth() + 1,d : this.getDate(),H : this.getHours(),M : this.getMinutes(),S : this.getSeconds()}
            for(k in f)
                format = format.replace('%' + k, f[k] < 10 ? "0" + f[k] : f[k]);
            return format;
        };

        $(document).ready(function () {
            $('#example').dataTable({
                "paging":   false,
                "ajax": "<spring:url value="/torrents"/>",
                "columns": [
                    { "data": "name" },
                    { "data": "episode" },
                    { "data": "date",
                        "render": function ( data ) {
                            return new Date(data).toLocaleFormat('%d.%m.%y %H:%M');
                        }
                    }
                ]
            });
        });
    </script>
</head>
<body>
<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>Name</th>
        <th>Episode</th>
        <th>Updated</th>
    </tr>
    </thead>

    <tfoot>
    </tfoot>
</table>
</body>
</html>
