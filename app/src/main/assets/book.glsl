uniform mat4 u_mvMatrix; 
uniform mat4 u_pMatrix;  	
const mediump float r = 80.0;
uniform float f;  			
attribute vec4 a_position;                  										
attribute vec2 a_texCoord;   													
varying vec2 v_texCoord;    														
void main()                                 										
{                                           										
                                    		   										
                            		   												
	mediump vec4 v =    u_mvMatrix * a_position ;  								
     mediump vec2 pd = vec2(0.8660,-0.5000);              		   				
     mediump vec2 vpmax = vec2(230.0,-400.0);              		   				
     mediump float vpmaxf = dot(pd, vpmax);              		   				
     mediump float vp = dot(pd, v.xy);                      		   				
     if (vp < f  + vpmaxf) {   													
     } else if (vp < f+ vpmaxf +r*3.1416) {                      		   		
         mediump float a = (vp-f-vpmaxf)/(r)-1.5708;                    			
         v.xyz += vec3((f+vpmaxf+r*cos(a)-vp)*pd, r+r*sin(a));      				
     } else {                    		   										
        v.xyz += vec3((2.0*(f+ vpmaxf -vp)+3.1416*(r))*pd, 2.0*r);                 
     }                    		   												
                                    		   										
   gl_Position = u_pMatrix  * v  ; 												
   v_texCoord = a_texCoord;  													
    				 																
}
precision mediump float;
varying vec2 v_texCoord;                            
uniform sampler2D s_baseMap;                        
//	uniform sampler2D s_lightMap;                       
void main()                                         
{                                                   
  vec4 baseColor;                                   
  vec4 lightColor;                                  
                                                    
  gl_FragColor = texture2D( s_baseMap, v_texCoord );   
//	  lightColor = texture2D( s_lightMap, v_texCoord ); 
//	  gl_FragColor = baseColor * (lightColor + 0.25);   
}
mediump vec4 v =    u_mvMatrix * a_position;
mediump vec2 pd = vec2(0.8660, -0.5000);
mediump vec2 vpmax = vec2(230.0, -400.0);
mediump float vpmaxf = dot(pd, vpmax);
mediump float vp = dot(pd, v.xy);
if (vp < f  + vpmaxf) {
} else if (vp < f+ vpmaxf +r*3.1416) {
    mediump float a = (vp-f-vpmaxf)/(r)-1.5708;
    v.xyz += vec3((f+vpmaxf+r*cos(a)-vp)*pd, r+r*sin(a));
} else {
    v.xyz += vec3((2.0*(f+ vpmaxf -vp)+3.1416*(r))*pd, 2.0*r);
}

gl_Position = u_pMatrix  * v;
v_texCoord = a_texCoord;

}                                           										;
precision mediump float;
varying vec2 v_texCoord;                            
uniform sampler2D s_baseMap;                        
//	uniform sampler2D s_lightMap;                       
void main()                                         
{                                                   
  vec4 baseColor;                                   
  vec4 lightColor;                                  
                                                    
  gl_FragColor = texture2D( s_baseMap, v_texCoord );   
//	  lightColor = texture2D( s_lightMap, v_texCoord ); 
//	  gl_FragColor = baseColor * (lightColor + 0.25);   
}                                                   ;
