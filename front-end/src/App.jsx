import { useState } from 'react'
import './App.css'
import '@chatscope/chat-ui-kit-styles/dist/default/styles.min.css';
import { MainContainer, ChatContainer, MessageList, Message, MessageInput, TypingIndicator } from '@chatscope/chat-ui-kit-react';

function App() {
  const [messages, setMessages] = useState([
    {
      message: "Hello, I'm ChatGPT! Ask me anything!",
      sentTime: "just now",
      sender: "ChatGPT"
    }
  ]);
  const [isTyping, setIsTyping] = useState(false);

  const handleSend = async (message) => {
    const newMessage = {
      message,
      direction: 'outgoing',
      sender: "user"
    };

    const newMessages = [...messages, newMessage];
    
    setMessages(newMessages);

    setIsTyping(true);
    await processMessageToBackEnd(newMessages);
  };

  async function processMessageToBackEnd(chatMessages) {

    let apiMessages = chatMessages.map((messageObject) => {
      let role = "";
      if (messageObject.sender === "ChatGPT") {
        role = "assistant";
      } else {
        role = "user";
      }
      return { role: role, content: messageObject.message }
    });

    const chatRequest = {
      "messages": [
        ...apiMessages
      ]
    }

    await fetch('http://localhost:8080/api/v1/chat',
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(chatRequest)
      }).then((data) => {
        if (data.ok) {
          return data.json();
        } else if (data.status == 401) {
          throw new Error('Your API key is invalid, please retry with another API key');
        } else if (data.status == 429) {
          throw new Error('You exceeded your current quota, please check your plan and billing details');
        } else if (data.status == 500) {
          throw new Error('The server had an error while processing your request, retry your request after a brief wait');
        } else {
          throw new Error('INTERNAL SERVER ERROR, please refresh the page and try again')
        }
      }).then((data) => {
        console.log(data);
        setMessages([...chatMessages, {
          message: data.choices[0].message.content,
          sender: "ChatGPT"
        }]);
        setIsTyping(false);
      }).catch((error) => {
        console.log("Error", error);
        setIsTyping(false);
        window.alert(error);
      });
  }

  return (
    <div className="App">
      <div style={{ position:"relative", height: "800px", width: "700px"  }}>
        <MainContainer>
          <ChatContainer>       
            <MessageList 
              scrollBehavior="smooth" 
              typingIndicator={isTyping ? <TypingIndicator content="ChatGPT is typing" /> : null}
            >
              {messages.map((message, i) => {
                return <Message key={i} model={message} />
              })}
            </MessageList>
            <MessageInput placeholder="Type message here" onSend={handleSend} />        
          </ChatContainer>
        </MainContainer>
      </div>
    </div>
  )
}

export default App
