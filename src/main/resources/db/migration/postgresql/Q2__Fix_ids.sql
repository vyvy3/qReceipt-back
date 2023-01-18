select setval('qreceipt_user_id_seq', max(id)) from qreceipt_user;
select setval('qreceipt_email_verification_id_seq', max(id)) from qreceipt_email_verification;
select setval('qreceipt_role_id_seq', max(id)) from qreceipt_role;