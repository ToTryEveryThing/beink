<template>
    <button @click="chat">
        <p>Guess</p>
        <svg stroke-width="4" stroke="currentColor" viewBox="0 0 24 24" fill="none" class="h-6 w-6" xmlns="http://www.w3.org/2000/svg">
            <path d="M14 5l7 7m0 0l-7 7m7-7H3" stroke-linejoin="round" stroke-linecap="round"></path>
        </svg>
    </button>
</template>

<script setup>
import { useStore } from 'vuex';
import router from '../router/index'
import { warning } from '@/utiles/message';
const store = useStore()
const chat=()=>{
    if(store.state.is_login===true)
      router.push("/more/guess")
    else
      warning("请登录后操作")
}
</script>

<style scoped>
button {
  cursor: pointer;
    padding: 0;
    margin: 0;
    border: none;
    background: none;
  }
  
  button {
    margin: 0 auto;
    text-align: center;
    --primary-color: var(--el-button-text-color);
    --hovered-color: #c84747;
    position: relative;
    display: flex;
    font-weight: 600;
    font-size: 20px;
    gap: 0.5rem;
    align-items: center;
  }
  
  button p {
    margin: 0;
    position: relative;
    font-size: 20px;
    color: var(--primary-color)
  }
  
  button::after {
    position: absolute;
    content: "";
    width: 0;
    left: 0;
    bottom: -7px;
    background: var(--hovered-color);
    height: 2px;
    transition: 0.3s ease-out;
  }
  
  button p::before {
    position: absolute;
  /*   box-sizing: border-box; */
    content: "Guess";
    width: 0%;
    inset: 0;
    color: var(--hovered-color);
    overflow: hidden;
    transition: 0.3s ease-out;
  }
  
  button:hover::after {
    width: 100%;
  }
  
  button:hover p::before {
    width: 100%;
  }
  
  button:hover svg {
    transform: translateX(4px);
    color: var(--hovered-color)
  }
  
  button svg {
    color: var(--primary-color);
    transition: 0.2s;
    position: relative;
    width: 15px;
    transition-delay: 0.2s;
  }

</style>