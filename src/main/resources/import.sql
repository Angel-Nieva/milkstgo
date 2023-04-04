INSERT INTO proveedores(codigo,nombre,categoria,retencion) VALUES ("13001", "Alimentos Valle Central", "A", "No");
INSERT INTO proveedores(codigo,nombre,categoria,retencion) VALUES ("10001", "Chilolac", "B", "Si");
INSERT INTO proveedores(codigo,nombre,categoria,retencion) VALUES ("10002", "Colun", "C", "No");

INSERT INTO planilla_Pagos(quincena,codigo_proveedor,nombre_proveedor,kls_leche,dias_envio_leche,avg_Kls_leche, variacion_leche,grasa,variacion_grasa,solidos_totales,variacion_st,pago_leche,pago_grasa,pago_st,bonificacion_frecuencia,dct_leche,dct_grasa,dct_st,pago_total,monto_retencion,monto_final) VALUES ("2023-02-17", " ", "Alimentos Valle Central", 0,0,0,0, 32,0,20,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO planilla_Pagos(quincena,codigo_proveedor,nombre_proveedor,kls_leche,dias_envio_leche,avg_Kls_leche, variacion_leche,grasa,variacion_grasa,solidos_totales,variacion_st,pago_leche,pago_grasa,pago_st,bonificacion_frecuencia,dct_leche,dct_grasa,dct_st,pago_total,monto_retencion,monto_final) VALUES ("2023-03-1", "13001", "Alimentos Valle Central", 600,0,0,0, 32,0,20,0,0,0,0,0,0,0,0,0,0,0);

INSERT INTO planilla_pagos(quincena,codigo_proveedor,nombre_proveedor,kls_leche,dias_envio_leche,avg_Kls_leche,variacion_leche,grasa,variacion_grasa,solidos_totales,variacion_st,pago_leche,pago_grasa,pago_st,bonificacion_frecuencia,dct_leche,dct_grasa,dct_st,pago_total,monto_retencion,monto_final) VALUES ("2023-03-1", "10001", "Chilolac", 600,0,0,0, 20,0,8,0,0,0,0,0,0,0,0,0,0,0 );

INSERT INTO planilla_pagos(quincena,codigo_proveedor,nombre_proveedor,kls_leche,dias_envio_leche,avg_Kls_leche, variacion_leche,grasa,variacion_grasa,solidos_totales,variacion_st,pago_leche,pago_grasa,pago_st,bonificacion_frecuencia,dct_leche,dct_grasa,dct_st,pago_total,monto_retencion,monto_final) VALUES ("2023-03-1", "10002", "Colun", 700,0,0,0, 20,0,8,0,0,0,0,0,0,0,0,0,0,0 );
