-- Create the database
CREATE DATABASE assignment_4;
USE assignment_4;

CREATE TABLE HW (
	row_num INT PRIMARY KEY,
    first_name VARCHAR(25),
    last_name VARCHAR(25),
    salary INT
);

INSERT INTO HW(row_num, first_name, last_name, salary)
	values
    (1, 'Karen', 'Colmenares', 2500),
    (2, 'Guy', 'Himuro', 2600),
    (3, 'Irene', 'Mikkilnieni', 2700),
    (4, 'Sigal', 'Tobias', 2800),
    (5, 'Shelli', 'Baida', 2900),
    (6, 'Alexander', 'Khoo', 3100),
    (7, 'Britney', 'Everett', 3900),
    (8, 'Sarah', 'Bell', 4000),
    (9, 'Diana', 'Lorentz', 4200),
    (10, 'Jennifer', 'Whalen', 4400),
    (11, 'David', 'Austin', 4800),
    (12, 'Valli', 'Pataballa', 4800),
    (13, 'Bruce', 'Ernst', 6000),
    (14, 'Pat', 'Fay', 6000),
    (15, 'Charles', 'Johnson', 6200);
    
SELECT *,
	FIRST_VALUE(salary) OVER (ORDER BY salary ASC) AS LowestSalary
    FROM HW
    ORDER BY salary ASC
    LIMIT 1;
    
SELECT *,
	FIRST_VALUE(salary) OVER (ORDER BY salary DESC) AS HighestSalary
    FROM HW
    ORDER BY salary DESC
    LIMIT 1;
    
SELECT *
FROM ( 
	SELECT *,
	LEAD(salary, 2) OVER (ORDER BY salary) as Q3
    FROM HW
) AS EmployeeLead
WHERE first_name = 'Guy' AND last_name = 'Himuro'; 

SELECT * 
FROM (
	SELECT *,
    LAG(salary, 4) OVER (ORDER BY salary) as Q4
    FROM HW
) AS EmployeeLag
WHERE first_name = 'Pat' AND last_name = 'Fay';

SELECT * 
FROM (
	SELECT *,
    RANK() OVER (ORDER BY salary ASC) AS Rank_asc
	FROM HW
) as rank0;

SELECT * 
FROM (
	SELECT *,
    DENSE_RANK() OVER (ORDER BY salary ASC) AS DenseRank
    FROM HW
)as DenseRank1;

SELECT * 
FROM (
	SELECT *,
    DENSE_RANK() OVER (ORDER BY salary ASC) AS DenseRank
    FROM HW
)as DenseRank1
WHERE (first_name = 'Valli' AND last_name = 'Pataballa') OR (first_name = 'Bruce' AND last_name = 'Ernst');

SELECT * 
FROM (
	SELECT *,
    ROW_NUMBER() OVER (ORDER BY salary ASC) AS Rownumber
    FROM HW
)as Rowtable
WHERE (first_name = 'Irene' AND last_name = 'Mikkilnieni') OR (first_name = 'Sarah' AND last_name = 'Bell');

SELECT * 
FROM (
	SELECT *,
    (PERCENT_RANK() OVER (ORDER BY salary ASC)) * 100  AS Percent
    FROM HW
)as PercentRank;

SELECT * 
FROM (
	SELECT *,
    (PERCENT_RANK() OVER (ORDER BY salary ASC))  AS Percent1,
    Round(CUME_DIST() OVER (ORDER BY salary ASC),2) AS Cum
    FROM HW
)as Cume;

	SELECT *,
    NTILE(4) OVER (ORDER BY salary ASC) AS tile
    FROM HW;
