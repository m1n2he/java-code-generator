package com.cc.jcg.json;


import java.io.File;

import org.junit.jupiter.api.Test;

import com.cc.jcg.MBundle;
import com.cc.jcg.MPackage;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MJsonGeneratorTest {

    private final File file = new File("src-test/com/cc/jcg/json/order.json");

    @Test
    void test1() throws Exception {
	// ----------------------------------------------------------------------------------------------------------------
	MBundle.EXCLUDE_GENERATED_ANNOTATION.set(false);
	MBundle.GENERATE_READONLY.set(true);
	// ----------------------------------------------------------------------------------------------------------------
	MBundle bundle = new MBundle(new File("src-generated-t"));
	MPackage pckg = bundle.newPackage("com.cc.jcg.jsonb");
	// ----------------------------------------------------------------------------------------------------------------
	MJsonGenerator generator = new MJsonGenerator(pckg);
	generator.setClassesNamePrefix("Json");
	generator.generateJsonBeanFor(file, "Order");
	// ----------------------------------------------------------------------------------------------------------------
	bundle.generateCode(false);
	// ----------------------------------------------------------------------------------------------------------------
    }

//    @Test
//    void test2() throws Exception {
//	// ----------------------------------------------------------------------------------------------------------------
//	JsonOrder order = MJsonGenerator.getBean(JsonOrder.class, file);
//	assertNotNull(order.getName());
//	assertNotNull(order.getToken());
//	assertNotNull(order.getBillingAddress().getToken());
//	assertNotNull(order.getBillingAddress());
//	assertNotNull(order.getTaxes());
//	System.out.println(MJsonGenerator.toJson(order));
//	MJsonGenerator.toJsonFile(order, new File("src-test/com/cc/jcg/json/order2.json"));
//	// ----------------------------------------------------------------------------------------------------------------
//    }
}
