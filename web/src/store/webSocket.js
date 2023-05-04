import { open2 } from "@/utiles/message";
export default {
    state: {
        socket:null,
        userList:[]
    },
    actions: {
        connectToWebSocket({ commit }, { id, name, token }) {
            const socket = new WebSocket(`wss://so.beink.cn/websocket/${id}/${name}/${"Bearer " + token}`);
            socket.onopen = () => {
              commit("updateWebSocket", socket);
              // console.log("WebSocket connected");
            };
            socket.onerror = (error) => {
              console.error("WebSocket error", error);
            };
            socket.onclose = () => {
            };
            socket.onmessage = msg =>{
              let value = JSON.parse(msg.data)
              if(value.author==='about'){
                open2(value.message)
              }
            }
            return socket;
          },
    },
    mutations: {
        updateWebSocket(state, socket) {
            state.socket = socket;
        },
    },
}
