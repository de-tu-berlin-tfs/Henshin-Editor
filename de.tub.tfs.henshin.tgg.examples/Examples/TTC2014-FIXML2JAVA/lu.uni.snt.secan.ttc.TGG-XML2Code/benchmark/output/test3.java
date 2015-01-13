/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
class Order {
    Order() {
    }
}

class FIXML {
    FIXMLMessage FIXMLMessage_object = new FIXMLMessage();

    FIXML() {
    }

    FIXML(FIXMLMessage FIXMLMessage_) {
	this.FIXMLMessage_object = FIXMLMessage_;
    }
}

class FIXMLMessage {
    ApplicationMessage ApplicationMessage_object = new ApplicationMessage();

    FIXMLMessage() {
    }

    FIXMLMessage(ApplicationMessage ApplicationMessage_) {
	this.ApplicationMessage_object = ApplicationMessage_;
    }
}

class ApplicationMessage {
    Order Order_object = new Order();

    ApplicationMessage() {
    }

    ApplicationMessage(Order Order_) {
	this.Order_object = Order_;
    }
}

class ClOrdID {
    String __VALUE = "ORD_1";

    ClOrdID() {
    }

    ClOrdID(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class HandInst {
    String Value = "2";

    HandInst() {
    }

    HandInst(String Value) {
	this.Value = Value;
    }
}

class MinQty {
    String __VALUE = "1000";

    MinQty() {
    }

    MinQty(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class Instrument {
    Symbol Symbol_object = new Symbol();
    IDSource IDSource_object = new IDSource();
    SecurityID SecurityID_object = new SecurityID();

    Instrument() {
    }

    Instrument(Symbol Symbol_, IDSource IDSource_, SecurityID SecurityID_) {
	this.Symbol_object = Symbol_;
	this.IDSource_object = IDSource_;
	this.SecurityID_object = SecurityID_;
    }
}

class Side {
    String Value = "1";

    Side() {
    }

    Side(String Value) {
	this.Value = Value;
    }
}

class TransactTime {
    String __VALUE = "20000907-09:25:56";

    TransactTime() {
    }

    TransactTime(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class OrderQuantity {
    OrderQty OrderQty_object = new OrderQty();

    OrderQuantity() {
    }

    OrderQuantity(OrderQty OrderQty_) {
	this.OrderQty_object = OrderQty_;
    }
}

class OrderType {
    LimitOrder LimitOrder_object = new LimitOrder();

    OrderType() {
    }

    OrderType(LimitOrder LimitOrder_) {
	this.LimitOrder_object = LimitOrder_;
    }
}

class Currency {
    String Value = "USD";

    Currency() {
    }

    Currency(String Value) {
	this.Value = Value;
    }
}

class Rule80A {
    String Value = "A";

    Rule80A() {
    }

    Rule80A(String Value) {
	this.Value = Value;
    }
}

class Symbol {
    String __VALUE = "EK";

    Symbol() {
    }

    Symbol(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class IDSource {
    String __VALUE = "1";

    IDSource() {
    }

    IDSource(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class SecurityID {
    String __VALUE = "277461109";

    SecurityID() {
    }

    SecurityID(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class OrderQty {
    String __VALUE = "5000";

    OrderQty() {
    }

    OrderQty(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class LimitOrder {
    String Value = "2";
    Price Price_object = new Price();

    LimitOrder() {
    }

    LimitOrder(String Value, Price Price_) {
	this.Value = Value;
	this.Price_object = Price_;
    }
}

class Price {
    String __VALUE = "62.5";

    Price() {
    }

    Price(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}