import type { Config } from 'tailwindcss'

const config: Config = {
  mode: 'jit',
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        'primary-50': '#FFE4E9',
        'primary-100': '#FFC9D3',
        'primary-200': '#FFAEBD',
        'primary-300': '#FF93A7',
        'primary-400': '#FF6A85',
        'primary-500': '#FF4365',
        'primary-600': '#DA3956',
        'primary-700': '#B62F48',
        'primary-800': '#912639',
        'primary-900': '#6D1C2B',
        'primary-950': '#48131C',
        'secondary': '#FFECC7',
        'secondary-medium': '#FFFBF3',
        'secondary-light': '#FFFEFC',
        'text': '#240F0F',
        'gray-dark': '#A8A8A8',
        'gray-medium': '#EDEBEC',
        'gray-light': '#FFFCFD',
      },
      fontFamily: {
        pretendard: ['Pretendard', 'Arial', 'sans-serif'],
      },
      height: {
        tabbar: '4rem',
      },
    },
  },
  plugins: [],
}
export default config
