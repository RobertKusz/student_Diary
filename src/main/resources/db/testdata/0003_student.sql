insert into
    student (first_name, last_name, pesel,login,password)
values
    ('John', 'Smith','98121600123','johny123','{noop}hasloJohn'),
    ('Bruno', 'Due','09876543211','bruno123','{noop}hasloBruno'),
    ('Mars', 'Montana','88773309876','mars123','{noop}hasloMars'),
    ('Bob', 'Deniro','11223345678','bob123','{noop}hasloBob');

insert into
    student_subjects(student_id, subject_id)
values
    (1,1),
    (1,2),
    (1,3),
    (1,4),
    (2,1),
    (2,2),
    (2,3),
    (2,4),
    (3,1),
    (3,2),
    (3,3),
    (3,4),
    (4,1),
    (4,2),
    (4,3),
    (4,4);

