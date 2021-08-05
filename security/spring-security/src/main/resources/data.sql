INSERT INTO users (username, password, enabled)
            values(
                'ram',
                'ram',
                'true'
            );
INSERT INTO users (username, password, enabled)
            values(
                'sam',
                'sam',
                'true'
            );
INSERT INTO authorities (username, authority)
            values(
            'ram', 'ROLE_USER'
            );
INSERT INTO authorities (username, authority)
            values(
            'sam', 'ROLE_ADMIN'
            );