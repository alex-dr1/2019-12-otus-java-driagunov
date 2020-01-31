Вывод:
G1 лучший сборщик, он обеспечивает минимальное время остановки всего приложения stop-the-world.
В молодом поколении G1 производит сборку без stop-the-world отдельным потоком.
<table>
   <caption>Таблица замеров с OOM примерно через 5 минут<br>
   после начала работы.
</caption>
   <tr><td></td><th>-Xms1g<br>-Xmx1g<br>loopCounter = 600</th><th>-Xms2g<br>-Xmx2g<br>loopCounter = 1200</th><th>-Xms4g<br>-Xmx4g<br>loopCounter = 2400</th></tr>
   <tr>
       <td>Parallel Collector</td>
       <td><br>Scavenge 6 (1.387s)<br>MarkSweep 3 (5.274s)</td>
       <td><br>Scavenge 6 (2.248s)<br>MarkSweep 4 (13.065s)</td>
       <td><br>Scavenge 4 (3.149s)<br>MarkSweep 5 (36.631s)</td>
   </tr>
   <tr>
        <td>Serial Collector</td>
        <td>Copy 6 (1.015s)<br>MarkSweepCompact 4 (5.093s)</td>
        <td>Copy 7 (2.418s)<br>MarkSweepCompact 28 (77.985s)</td>
        <td>Copy 6 (3.642s)<br>MarkSweepCompact 3 (12.841s)</td>
   </tr>
   <tr>
       <td>CMS</td>
       <td>ParNew 5 (1.742s)<br>ConcurrentMarkSweep 3 (4.234s)</td>
       <td>ParNew 10 (3.438s)<br>ConcurrentMarkSweep 6 (16.838s)</td>
       <td>ParNew 19 (6.344s)<br>ConcurrentMarkSweep 12 (63.521s)</td>
   </tr>
   <tr>
        <td>G1</td>
        <td>Young 27 (0.954s)<br>Old 2 (1.5s)</td>
        <td>Young 35 (2.418s)<br>Old 3 (4.8s)</td>
        <td>Young 52 (4.399s)<br>Old 15 (45.22s)</td>
   </tr>
</table>
