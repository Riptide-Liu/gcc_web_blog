const state = {
    type1: '',
}

const mutations = {
    setType1(state, {payload}){
        state.type1 = payload
    },
}

const actions = {
  
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}