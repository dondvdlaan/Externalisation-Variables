import axios, { AxiosResponse, Method }                   from "axios";
import { Dispatch, SetStateAction, useEffect, useState }  from "react";
import useErrorHandler from "../modules/errorHandling/ErrorHandler";

// ********************* Constanten und Typen *********************
export const baseUrl  = process.env.REACT_APP_BACKEND_URL;
//export const baseUrl  = `http://localhost:8080/`;



type SetState<T> = Dispatch<SetStateAction<T>>;

/*
 * Useful for http data as a dependency in rendering
 *
 * @param   method  [Method] : http method
 * @param   path    [string] : relative path to baseUrl
 * @return, Response Data
 */
export function useApi<T>(path: string): [T | undefined, SetState<T | undefined>] 
  {
    const [data, setData] = useState<T>();

    useEffect(() => {
      api("GET", path, setData)
    }, [path]);

    // console.log("data "+JSON.stringify(data))
    return [data, setData];
  }

/*
 * This useApi2 function was copied from useApi above and changed as follows:
 * - default state is an empty array[], so the map function in jsx is not stuck
 * - an if condition within the useEffect was added, which causes the useApi2 to 
 * wait till the condition arrives
 *
 * @param path      [string]  : relative path to baseUrl
 * @param condition [string]  : Api2 will wait till condition is fulfilled
 * @return, Response Data
 */
export function useApi2<T>(path: string, condition: string)
: [T | undefined, SetState<T | undefined>]  {

const [data, setData] = useState<T>();

useEffect(() => {

console.log("condition: ",condition);

if(condition) api("GET", path, setData);
}, [condition]);

return [data, setData];
}
/*
 * Useful for http data as a dependency in rendering
 *
 * @param   method  [Method] : http method
 * @param   path    [string] : relative path to baseUrl
 * @return, Response Data
 */
export function useApi3<T>(path: string): [T | undefined, SetState<T | undefined>] 
  {
    const [data, setData] = useState<T>();
    const setVisibleError = useErrorHandler();

    useEffect(() => {
      //api("GET", path, setData)

        const config ={
            method: "GET",
            url: `${baseUrl}${path}`,
            data,
        } ;

        console.log('API config:',config);
        
        axios(config)
        .then((response: AxiosResponse<T>) => {
          // console.log("response.data", response.data)  
          return setData(response.data);
        })
        .catch(err =>{
          console.log({err})
          setVisibleError(`${err.response.data.error}`, err," Please contact the IT department") ;
        } )
    }, [path]);

    // console.log("data "+JSON.stringify(data))
    return [data, setData];
  }
/*
 * Useful for calls on events or in conditions
 *
 * @param method      [Method]    : http method
 * @param path        [string]    : relative path to baseUrl
 * @param callback    [function]  : callback optionally
 * @param data        [object]    : body data
 * @return callback   [function]  : callback, gets `response.data` as an argument
 * 
 */
export function api<T>(
    method: Method, path: string,  callback?: any, data = {}): void {

        const config ={
            method,
            url: `${baseUrl}${path}`,
            data,
        } ;

        console.log('API config:',config);
        
        axios(config)
        .then((response: AxiosResponse<T>) => {
          // console.log("response.data", response.data)  
          return callback(response.data);
        })
}

/**
 * Simplified Api for direct calling server and without callback function
 * 
 * @param   method  [Method]      : http method
 * @param   path    [string]      : relative path to baseUrl
 * @param   data    [JSON]        : optionally data can be send with message
 * @return  axios   [AxiosPromise]: return message to be captured with .then
 */
export function ApiSimplified<T>(method: Method, path: string, data = {}) {

      const config ={
          method,
          url: `${baseUrl}${path}`,
          data,
      } ;

      console.log('API simple config:',config);

       return axios(config)
       // .then((response: AxiosResponse<T>) => response.data);
       // .then((response: AxiosResponse<T>) => console.log('response.data: ', response.data));
}