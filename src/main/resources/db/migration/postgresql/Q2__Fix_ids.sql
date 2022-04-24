select setval('qrecipient_user_id_seq', max(id)) from qrecipient_user;
select setval('qrecipient_email_verification_id_seq', max(id)) from qrecipient_email_verification;
select setval('qrecipient_role_id_seq', max(id)) from qrecipient_role;