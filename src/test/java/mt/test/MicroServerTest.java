package mt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mt.Order;
import mt.comm.ServerComm;
import mt.comm.ServerSideMessage;
import mt.comm.ServerSideMessage.Type;
import mt.server.MicroServer;

/**
 * Test Class for MicroServer
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class MicroServerTest {
	
	private MicroServer ms;
	
	@Mock
	private ServerComm serverComm;
	
	@Mock
	private ServerSideMessage msg1;
	
	@Mock
	private ServerSideMessage msg2;
	
	@Mock
	private ServerSideMessage msg3;
	
	@Mock
	private ServerSideMessage msg4;
	
	@Mock
	private ServerSideMessage msg5;
	
	@Mock
	private ServerSideMessage msg6;
	
	@Mock
	private ServerSideMessage msg7;
	
	@Mock
	private ServerSideMessage msg8;
	
	@Mock
	private ServerSideMessage msg9;
	
	@Mock
	private ServerSideMessage msg10;
	
	@Mock
	private ServerSideMessage msg11;
	
	@Mock
	private ServerSideMessage msg12;

	@Mock
	private ServerSideMessage msg13;

	@Mock
	private ServerSideMessage msg14;

	@Mock
	private ServerSideMessage msg15;

	@Mock
	private ServerSideMessage msg16;
	
	@Mock
	private ServerSideMessage msg17;
	
	@Before
	public void setup(){
		ms = new MicroServer();
		
		when(msg1.getType()).thenReturn(Type.CONNECTED);
		when(msg1.getOrder()).thenReturn(null);
		when(msg1.getSenderNickname()).thenReturn("userA");
		
		when(msg2.getType()).thenReturn(Type.NEW_ORDER);
		when(msg2.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 15, 20.0));
		when(msg2.getSenderNickname()).thenReturn("userA");
		
		when(msg3.getType()).thenReturn(Type.CONNECTED);
		when(msg3.getOrder()).thenReturn(null);
		when(msg3.getSenderNickname()).thenReturn("userB");
		
		when(msg4.getType()).thenReturn(Type.NEW_ORDER);
		when(msg4.getOrder()).thenReturn(Order.createBuyOrder("userB", "MSFT", 10, 21.0));
		when(msg4.getSenderNickname()).thenReturn("userB");
		
		when(msg5.getType()).thenReturn(Type.DISCONNECTED);
		when(msg5.getOrder()).thenReturn(null);
		when(msg5.getSenderNickname()).thenReturn("userA");
		
		when(msg6.getType()).thenReturn(Type.DISCONNECTED);
		when(msg6.getOrder()).thenReturn(null);
		when(msg6.getSenderNickname()).thenReturn("userB");
		
		when(msg7.getType()).thenReturn(null);
		when(msg7.getOrder()).thenReturn(null);
		when(msg7.getSenderNickname()).thenReturn(null);
		
		when(msg8.getType()).thenReturn(Type.NEW_ORDER);
		when(msg8.getOrder()).thenReturn(Order.createBuyOrder("userB", "ISCTE", 15, 21.0));
		when(msg8.getSenderNickname()).thenReturn("userB");
		
		when(msg9.getType()).thenReturn(Type.NEW_ORDER);
		when(msg9.getOrder()).thenReturn(Order.createSellOrder("userA", "ISCTE", 10, 20.0));
		when(msg9.getSenderNickname()).thenReturn("userA");	
	
		when(msg10.getType()).thenReturn(Type.NEW_ORDER);
		when(msg10.getOrder()).thenReturn(null);
		when(msg10.getSenderNickname()).thenReturn("userA");
		
		when(msg11.getType()).thenReturn(Type.NEW_ORDER);
		when(msg11.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 9, 20.0));
		when(msg11.getSenderNickname()).thenReturn("userA");
		
		when(msg12.getType()).thenReturn(Type.NEW_ORDER);
		when(msg12.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 15, 20.0));
		when(msg12.getSenderNickname()).thenReturn("userA");
		
		when(msg13.getType()).thenReturn(Type.NEW_ORDER);
		when(msg13.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 15, 20.0));
		when(msg13.getSenderNickname()).thenReturn("userA");
		
		when(msg14.getType()).thenReturn(Type.NEW_ORDER);
		when(msg14.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 15, 20.0));
		when(msg14.getSenderNickname()).thenReturn("userA");
		
		when(msg15.getType()).thenReturn(Type.NEW_ORDER);
		when(msg15.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 15, 20.0));
		when(msg15.getSenderNickname()).thenReturn("userA");
		
		when(msg16.getType()).thenReturn(Type.NEW_ORDER);
		when(msg16.getOrder()).thenReturn(Order.createSellOrder("userA", "MSFT", 15, 20.0));
		when(msg16.getSenderNickname()).thenReturn("userA");
			
		when(msg17.getType()).thenReturn(Type.NEW_ORDER);
		when(msg17.getOrder()).thenReturn(Order.createBuyOrder("userA", "MSFT", 15, 20.0));
		when(msg17.getSenderNickname()).thenReturn("userA");
	}
	
	@After
	public void tearDown(){
		ms = null;
		serverComm = null;
	}

	/**
	 * Tests the instantiation of the MicroServer
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMicroServer() throws Exception {
		ms = null;
		ms = new MicroServer();
		assertNotNull(ms);
	}

	/**
	 * Tests a simple transaction of orders between two different users
	 * 
	 * @throws Exception
	 */
	@Test
	public void nominalTest() throws Exception {
		
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg2).thenReturn(msg3).thenReturn(msg4).thenReturn(null);
		
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendOrder("userB", Order.createSellOrder("userA", "MSFT", 5, 20.0) );
		verify(serverComm, atLeastOnce()).sendOrder("userB", Order.createBuyOrder("userB", "MSFT", 0, 21.0) );
		
		assertEquals(5, ms.getOrderMap().get("userA").iterator().next().getNumberOfUnits());
	}
	
	/**
	 * Tests the case where a not connected user tries to submit an order
	 * 
	 * Expected: An error message should be sent by serverComm
	 * 
	 * @throws Exception
	 */
	@Test
	public void userNotConnectedTest() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg2).thenReturn(null);
		
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendError(msg2.getSenderNickname(), "The user " + msg2.getSenderNickname() + " is not connected.");
	}
	
	/**
	 * Tests the subsmission of an order with an unrecognizable type
	 * 
	 * Expected: An error message should be sent by serverComm
	 * 
	 * @throws Exception
	 */
	@Test
	public void orderTypeNotRecognizedTest() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg7).thenReturn(null);
		
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendError(null, "Type was not recognized");
	}
	
	/**
	 * Tests the case where a ServerSideMessage is sent without any order
	 * 
	 * Expected: An error message should be sent by serverComm
	 * 
	 * @throws Exception
	 */
	@Test
	public void noOrderInMessageTest() throws Exception {		
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg10).thenReturn(null);	
		ms.start(serverComm);
		verify(serverComm, atLeastOnce()).sendError(msg10.getSenderNickname(), "There is no order in the message.");
	}
	
	/**
	 * Test the case where a sell order is sent
	 * 
	 * @throws Exception
	 */
	@Test
	public void startProcessSellOrderTest() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg3).thenReturn(msg8).thenReturn(msg1).thenReturn(msg9).thenReturn(null);
		
		ms.start(serverComm);
		verify(serverComm, atLeastOnce()).sendOrder("userB",Order.createBuyOrder("userB", "ISCTE", 5, 21.0) );
		
	}
	
	/**
	 * Test the case where a buy order is sent
	 * 
	 * @throws Exception
	 */
	@Test
	public void startProcessBuyOrderTest() throws Exception {		
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg2).thenReturn(msg3).thenReturn(msg4).thenReturn(msg5).thenReturn(msg6).thenReturn(null);
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendOrder("userA", Order.createSellOrder("userA", "MSFT", 5, 20.0));
	}
	
	
	/**
	 * Tests the case where an already connected user sends a connection message again
	 * 
	 * Expected: An error message should be sent by serverComm
	 * 
	 * @throws Exception
	 */
	@Test
	public void userAlreadyConnectedTest() throws Exception {		
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg1).thenReturn(null);
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendError(msg1.getSenderNickname(), "The user " + msg1.getSenderNickname() + " is already connected.");
	}

	/**
	 * Tests the case where a user submits an order with less than 10 units
	 * 
	 * Expected: An error message should be sent by serverComm
	 * 
	 * @throws Exception
	 */
	@Test
	public void minimumOrderQuantityTest() throws Exception {
		when(serverComm.getNextMessage()).thenReturn(msg1).thenReturn(msg11).thenReturn(null);
		
		ms.start(serverComm);
		
		verify(serverComm, atLeastOnce()).sendError(msg11.getSenderNickname(), "This order can't be submitted because the client " + msg11.getSenderNickname()
		+ " submited an order with less than 10 units.");
		assertTrue(ms.getOrderMap().get("userA").isEmpty());	
	}	
}
