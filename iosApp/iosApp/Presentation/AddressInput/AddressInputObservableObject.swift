//
//  AddressInputObservableObject.swift
//  iosApp
//
//  Created by Andy O'Brien on 12/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared
public class AddressInputObservableObject : ObservableObject {

    var viewModel : AddressInputViewModel

   /**
    *
    * state flow acts as a state for swift ui here
    *
    */
    @Published private(set) var state: AddressInputScreenState
    
    @Published private(set) var uiEvent: BaseUiEvent
        
        
    /**
     **
     *fusing the .asObserveable extension funstion we get the wrapped viewmodel and the stateflow
     */
    init(wrapped: AddressInputViewModel) {

        viewModel = wrapped
        state = wrapped.state.value as! AddressInputScreenState
        uiEvent = GenericUiEvent.None()
        
        (wrapped.state.asPublisher() as AnyPublisher<AddressInputScreenState, Never>)
            .receive(on: RunLoop.main)
            .assign(to: &$state)
        
        (wrapped.uiEvent.asPublisher() as AnyPublisher<BaseUiEvent, Never>)
            .receive(on: RunLoop.main)
            .assign(to: &$uiEvent)
        
    }

}

public extension AddressInputViewModel {

    func asObserveableObject() -> AddressInputObservableObject{
        return AddressInputObservableObject(wrapped: self)
    }

}
