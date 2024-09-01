CREATE TABLE users (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);


CREATE TABLE wallets (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    balance INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE wallet_denominations (
    wallet_id UUID REFERENCES wallets(id) ON DELETE CASCADE,
    denomination INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (wallet_id, denomination)
);


CREATE TABLE loyalty_programs (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    program_name TEXT NOT NULL,
    description TEXT
);

CREATE TABLE user_loyalty_programs (
    user_id UUID REFERENCES users(id),
    loyalty_program_id UUID REFERENCES loyalty_programs(id),
    PRIMARY KEY (user_id, loyalty_program_id)
);


CREATE TABLE tickets (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    purchase_time TIMESTAMPTZ DEFAULT now(),
    ticket_type TEXT NOT NULL,
    numbers_chosen JSONB,
    amount_spent INTEGER NOT NULL,
    loyalty_program_id UUID REFERENCES loyalty_programs(id)
);



-- Select a specific ticket and all information on it, including who placed it and their loyalty program:

SELECT t.*, u.name, u.email, lp.program_name
FROM tickets t
JOIN users u ON t.user_id = u.id
LEFT JOIN loyalty_programs lp ON t.loyalty_program_id = lp.id
WHERE t.id = 'specific-ticket-id';

-- Select the sum of the money spent by a user over a certain time frame for each loyalty program:

SELECT lp.program_name, SUM(t.amount_spent) AS total_spent
FROM tickets t
JOIN loyalty_programs lp ON t.loyalty_program_id = lp.id
JOIN users u ON t.user_id = u.id
WHERE u.email = 'user@example.com'
AND t.purchase_time BETWEEN 'start-date' AND 'end-date'
GROUP BY lp.program_name;


-- Select all the loyalty programs assigned to a user:

SELECT lp.*
FROM loyalty_programs lp
JOIN user_loyalty_programs ulp ON lp.id = ulp.loyalty_program_id
JOIN users u ON ulp.user_id = u.id
WHERE u.email = 'user@example.com';