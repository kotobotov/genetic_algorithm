# Parallel Genetic Algorithm with scala
initial version, try to using FP not fully)


i create for persanal use (train my bots on codingame and over competitions)

my goal was to create easy to reuse solution

second goal is perfomance (as evaluating function can be complex, so it's important to make as small
evaluatin as possible to evolve)

with small data and simple fittness function perfomance usual don't have any 
and perfomance for parallel solution can be wors

but than data is growing and fitness function is become really complex (for excample fitness function relaing on gameSimulation resultat)
parallel working is huge, also less ammount of fitness evaluation is important

само собой у вас нет никакого ТАРГЕТА, если бы вы знали таргет
вам не нужна была бы эволюция, эволюцию используете чтоб улучшать 
решение постепенно, и если вы знаете как оценить улучшение, тогда можно
приложить эволюционный алгоритм

поэтому в настройках моего алгоритма отсутствует таргет, 
можно настраивать только функцию оценки результата

я старался по максимуму оптимизировать параметры (размер отсечения
размер генерации популяции, размер отсечения популяции, размер мутации

)

## correctnes testing

use `sbt test`

usual string permutation and similarity evaluation

## perfomance evaluating

so i increase working time of fitness function with dummy operation like `sleep`
(only in tests)
to optimase solution to havy real workflow (not just string permutation and evaluating string similaryty as usual everyone do)

use `sbt perfomance`

## using

to reuse you need first discrabe 
вы просто указываете базу для генерации генов
какой длинны ген нужен
а также функцию оценки эволюции

пример ->

если это параметры функции - вы указываете несколько градаций этого параметра
для примера Сек("-6", "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5", "6")
так что можно использовать не только одно буквенные, но и много символьные комбинации в качестве
одного элемента гена

## идеи для развития
есть мысль добавить еще градиентный спуск по какому либо из параметров
идеи но пока нет задачи усложнять решение, и делать его более сложным

чтоб тоже можно было удобно указать какой параметр можно просто варьировать
как его вариьировать ну и схему как можно оценить типа градиентного спуска параметр и сделать попытку сразу его подобрать
