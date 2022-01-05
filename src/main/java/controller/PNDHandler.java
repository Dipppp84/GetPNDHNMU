package controller;

import DAO.DAO_PND;
import DAO.DaoFactory;
import entity.PND;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import utill.GsonUtil;
import utill.ServerHttp;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.newChunkedResponse;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class PNDHandler extends RouterNanoHTTPD.DefaultHandler {
    @Override
    public String getText() {
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            border-spacing: 0;\n" +
                "            empty-cells: hide;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            padding: 10px 20px;\n" +
                "            text-align: center;\n" +
                "            border-bottom: 1px solid #F4EEE8;\n" +
                "            transition: all 0.5s linear;\n" +
                "        }\n" +
                "\n" +
                "        td:first-child {\n" +
                "            text-align: left;\n" +
                "            color: #3D3511;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        th {\n" +
                "            padding: 10px 20px;\n" +
                "            color: #3D3511;\n" +
                "            border-bottom: 1px solid #F4EEE8;\n" +
                "            border-top-left-radius: 10px;\n" +
                "            border-top-right-radius: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .round-top {\n" +
                "            border-top-left-radius: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .round-bottom {\n" +
                "            border-bottom-left-radius: 10px;\n" +
                "        }\n" +
                "\n" +
                "        tr:hover td {\n" +
                "            background: #D1C7BF;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        .btn {\n" +
                "            -webkit-border-radius: 10em;\n" +
                "            -moz-border-radius: 10em;\n" +
                "            -ms-border-radius: 10em;\n" +
                "            -o-border-radius: 10em;\n" +
                "            border-radius: 10em;\n" +
                "            border: 0;\n" +
                "            color: #fff !important;\n" +
                "            margin: 6px;\n" +
                "            white-space: normal !important;\n" +
                "            word-wrap: break-word;\n" +
                "            display: inline-block;\n" +
                "            line-height: 1.25;\n" +
                "            text-align: center;\n" +
                "            vertical-align: middle;\n" +
                "            cursor: pointer;\n" +
                "            -webkit-user-select: none;\n" +
                "            -moz-user-select: none;\n" +
                "            -ms-user-select: none;\n" +
                "            user-select: none;\n" +
                "            padding: .5rem 1rem;\n" +
                "            font-size: 1rem;\n" +
                "            font-weight: 400;\n" +
                "            -webkit-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .16), 0 2px 10px 0 rgba(0, 0, 0, .12);\n" +
                "            -moz-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .16), 0 2px 10px 0 rgba(0, 0, 0, .12);\n" +
                "            box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .16), 0 2px 10px 0 rgba(0, 0, 0, .12);\n" +
                "        }\n" +
                "\n" +
                "        .btn {\n" +
                "            -webkit-transition: box-shadow .2s ease-out;\n" +
                "            -moz-transition: box-shadow .2s ease-out;\n" +
                "            -ms-transition: box-shadow .2s ease-out;\n" +
                "            transition: box-shadow .2s ease-out;\n" +
                "        }\n" +
                "\n" +
                "        .btn:hover {\n" +
                "            -webkit-box-shadow: 0 5px 11px 0 rgba(0, 0, 0, .18), 0 4px 15px 0 rgba(0, 0, 0, .15);\n" +
                "            -moz-box-shadow: 0 5px 11px 0 rgba(0, 0, 0, .18), 0 4px 15px 0 rgba(0, 0, 0, .15);\n" +
                "            box-shadow: 0 5px 11px 0 rgba(0, 0, 0, .18), 0 4px 15px 0 rgba(0, 0, 0, .15);\n" +
                "        }\n" +
                "\n" +
                "        .btn-delete { /*удалить*/\n" +
                "            border: 2px solid rgba(255, 65, 65, 0.82);\n" +
                "            color: #ff0000 !important;\n" +
                "            background-color: transparent;\n" +
                "        }\n" +
                "\n" +
                "        .btn-update { /*обновить список*/\n" +
                "            border: 2px solid #2BBBAD;\n" +
                "            color: #00695c !important;\n" +
                "            background-color: transparent;\n" +
                "        }\n" +
                "\n" +
                "        header {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Ведомости ПНД</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<article>\n" +
                "    <header>\n" +
                "        <h1>Ведомости ПНД:</h1>\n" +
                "    </header>\n" +
                "    <section>\n" +
                "        <button class=\"btn btn-update\" onclick=\"getPND()\">Обновить список</button> После загрузки страницы или нажатии на кнопку думает <b>+- 1 мин</b>. Для сортировки по колонке, нажмите на неё.  Для поиска по ключевому слову <b>Ctrl + F</b>\n" +
                "        <div id=\"PND\"></div>\n" +
                "    </section>\n" +
                "    <hr>\n" +
                "</article>\n" +
                "</main>\n" +
                "<script>\n" +
                "    const url = \"http://" + DaoFactory.IP + ":8089\";\n" +
                "\n" +
                "    async function getPND() {\n" +
                "        const response = await fetch(url + \"/getPND\", {\n" +
                "            method: 'GET',\n" +
                "        });\n" +
                "        const PND = await response.json();\n" +
                "        console.log(PND);\n" +
                "\n" +
                "        let table = document.createElement('table');\n" +
                "        let thead = document.createElement('thead');\n" +
                "        let tbody = document.createElement('tbody');\n" +
                "\n" +
                "        table.className = \"table_sort\";\n" +
                "        table.appendChild(thead);\n" +
                "        table.appendChild(tbody);\n" +
                "        let elmDep = document.getElementById('PND');\n" +
                "        elmDep.innerHTML = '';\n" +
                "        elmDep.appendChild(table);\n" +
                "\n" +
                "        let row_1 = document.createElement('tr');\n" +
                "        let heading_1 = document.createElement('th');\n" +
                "        heading_1.innerHTML = \"id\";\n" +
                "        let heading_2 = document.createElement('th');\n" +
                "        heading_2.innerHTML = \"Аббревиатура\";\n" +
                "        let heading_3 = document.createElement('th');\n" +
                "        heading_3.innerHTML = \"Название дисцп.\";\n" +
                "        let heading_4 = document.createElement('th');\n" +
                "        heading_4.innerHTML = \"Номер группы\";\n" +
                "        let heading_5 = document.createElement('th');\n" +
                "        heading_5.innerHTML = \"Преподаватель\";\n" +
                "        let heading_6 = document.createElement('th');\n" +
                "        heading_6.innerHTML = \"Номер ведомости\";\n" +
                "        let heading_7 = document.createElement('th');\n" +
                "        heading_7.innerHTML = \"Дата создания\";\n" +
                "\n" +
                "        row_1.appendChild(heading_1);\n" +
                "        row_1.appendChild(heading_2);\n" +
                "        row_1.appendChild(heading_3);\n" +
                "        row_1.appendChild(heading_4);\n" +
                "        row_1.appendChild(heading_5);\n" +
                "        row_1.appendChild(heading_6);\n" +
                "        row_1.appendChild(heading_7);\n" +
                "\n" +
                "        thead.appendChild(row_1);\n" +
                "\n" +
                "        if (PND.length == 0) {\n" +
                "            let rowNext = document.createElement('tr');\n" +
                "            let rowData1 = document.createElement('td');\n" +
                "            rowData1.textContent = \"\";\n" +
                "            let rowData2 = document.createElement('td');\n" +
                "            rowData2.textContent = \"Пусто\";\n" +
                "            rowNext.appendChild(rowData1);\n" +
                "            rowNext.appendChild(rowData2);\n" +
                "            tbody.appendChild(rowNext);\n" +
                "        } else {\n" +
                "\n" +
                "                let rowNext = document.createElement('tr');\n" +
                "                let rowData1 = document.createElement('td');\n" +
                "                rowData1.innerHTML = \"\";\n" +
                "                let rowData2 = document.createElement('td');\n" +
                "                rowData2.innerHTML = \"\";\n" +
                "                let rowData3 = document.createElement('td');\n" +
                "                rowData3.innerHTML = \"\";\n" +
                "                let rowData4 = document.createElement('td');\n" +
                "                rowData4.innerHTML = \"\";\n" +
                "                let rowData5 = document.createElement('td');\n" +
                "                rowData5.innerHTML = \"\";\n" +
                "                let rowData6 = document.createElement('td');\n" +
                "                rowData6.innerHTML = \"\";\n" +
                "                let rowData7 = document.createElement('td');\n" +
                "                rowData7.innerHTML = \"\";\n" +
                "\n" +
                "                rowNext.appendChild(rowData1);\n" +
                "                rowNext.appendChild(rowData2);\n" +
                "                rowNext.appendChild(rowData3);\n" +
                "                rowNext.appendChild(rowData4);\n" +
                "                rowNext.appendChild(rowData5);\n" +
                "                rowNext.appendChild(rowData6);\n" +
                "                rowNext.appendChild(rowData7);\n" +
                "\n" +
                "                tbody.appendChild(rowNext);\n" +
                "\n" +
                "            for (let i = 0; i < PND.length; i++) {\n" +
                "                let pndElement = PND[i];\n" +
                "\n" +
                "                let rowNext = document.createElement('tr');\n" +
                "                let rowData1 = document.createElement('td');\n" +
                "                rowData1.innerHTML = pndElement.id;\n" +
                "                let rowData2 = document.createElement('td');\n" +
                "                rowData2.innerHTML = pndElement.abvD;\n" +
                "                let rowData3 = document.createElement('td');\n" +
                "                rowData3.innerHTML = pndElement.nameD;\n" +
                "                let rowData4 = document.createElement('td');\n" +
                "                rowData4.innerHTML = pndElement.numberGroup;\n" +
                "                let rowData5 = document.createElement('td');\n" +
                "                rowData5.innerHTML = pndElement.nameTeacher;\n" +
                "                let rowData6 = document.createElement('td');\n" +
                "                rowData6.innerHTML = pndElement.numberPND;\n" +
                "                let rowData7 = document.createElement('td');\n" +
                "                rowData7.innerHTML = pndElement.dateCreation;\n" +
                "\n" +
                "                let rowData8 = document.createElement('td');\n" +
                "                let btnDelete = document.createElement(\"button\");\n" +
                "                const textDel = document.createTextNode(\"Удалить\");\n" +
                "                btnDelete.appendChild(textDel);\n" +
                "                btnDelete.className = \"btn btn-delete\";\n" +
                "                btnDelete.onclick = function () {\n" +
                "                    deletePND(pndElement.id)\n" +
                "                    getPND();\n" +
                "                };\n" +
                "                rowData8.appendChild(btnDelete);\n" +
                "                rowNext.appendChild(rowData1);\n" +
                "                rowNext.appendChild(rowData2);\n" +
                "                rowNext.appendChild(rowData3);\n" +
                "                rowNext.appendChild(rowData4);\n" +
                "                rowNext.appendChild(rowData5);\n" +
                "                rowNext.appendChild(rowData6);\n" +
                "                rowNext.appendChild(rowData7);\n" +
                "                rowNext.appendChild(rowData8);\n" +
                "\n" +
                "                tbody.appendChild(rowNext);\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        async function deletePND(id) {\n" +
                "            if (confirm(\"Удалить?\")) {\n" +
                "                await fetch(url + '/delPND?id=' + id, {\n" +
                "                    method: 'GET',\n" +
                "                    //headers: {'Accept': 'application/json'}\n" +
                "                });\n" +
                "                getPND();\n" +
                "            } else {\n" +
                "                getPND();\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        sort();\n" +
                "    }\n" +
                "\n" +
                "    getPND();\n" +
                "\n" +
                "    function sort() {\n" +
                "        const getCellValue = (tr, idx) => tr.children[idx].innerText || tr.children[idx].textContent;\n" +
                "\n" +
                "        const comparer = (idx, asc) => (a, b) => ((v1, v2) =>\n" +
                "                v1 !== '' && v2 !== '' && !isNaN(v1) && !isNaN(v2) ? v1 - v2 : v1.toString().localeCompare(v2)\n" +
                "        )(getCellValue(asc ? a : b, idx), getCellValue(asc ? b : a, idx));\n" +
                "\n" +
                "        // do the work...\n" +
                "        document.querySelectorAll('th').forEach(th => th.addEventListener('click', (() => {\n" +
                "            const table = th.closest('table');\n" +
                "            Array.from(table.querySelectorAll('tr:nth-child(n+2)'))\n" +
                "                .sort(comparer(Array.from(th.parentNode.children).indexOf(th), this.asc = !this.asc))\n" +
                "                .forEach(tr => table.appendChild(tr));\n" +
                "        })));\n" +
                "    }\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
        return body;
    }

    @Override
    public String getMimeType() {
        return null;
    }

    @Override
    public NanoHTTPD.Response.IStatus getStatus() {
        return NanoHTTPD.Response.Status.OK;
    }
}
